package com.hassuna.tech.htoffice.customer.application;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.postgresql.util.PSQLException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.hassuna.tech.htoffice.customer.application.entity.B2bCustomer;
import com.hassuna.tech.htoffice.customer.application.entity.ContactPerson;
import com.hassuna.tech.htoffice.customer.remote.payload.B2bCustomerPayload;
import com.hassuna.tech.htoffice.customer.remote.payload.CreateB2bCustomerPayload;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class B2bCustomerService {

  private static final Pattern VAT_PATTERN = Pattern.compile("^DE\\d{9}$");
  private static final Pattern TAX_ID_PATTERN = Pattern.compile("^\\d{10,11}$");
  private static final String UNIQUE_VIOLATION_STATE = "23505";
  private static final Pattern PG_KEY_DETAIL =
      Pattern.compile("Key \\(([^)]+)\\)=\\(([^)]+)\\) already exists\\.");

  private final B2bCustomerRepository b2bCustomerRepository;
  private final ContactPersonRepository contactPersonRepository;

  public B2bCustomerService(
      B2bCustomerRepository b2bCustomerRepository,
      ContactPersonRepository contactPersonRepository) {
    this.b2bCustomerRepository = b2bCustomerRepository;
    this.contactPersonRepository = contactPersonRepository;
  }

  public B2bCustomer getCustomer(final String customerId) {
    CustomCustomerIdGenerator.assertValidCustomerId(customerId);
    return b2bCustomerRepository
        .findByCustomerId(customerId)
        .orElseThrow(
            () -> {
              log.warn("Customer with ID {} not found", customerId);
              return new IllegalArgumentException("Customer not found with ID: " + customerId);
            });
  }

  public B2bCustomer createCustomer(CreateB2bCustomerPayload requestBody)
      throws IllegalArgumentException {

    validateCustomerData(requestBody);

    ContactPerson contactPerson = null;

    if (requestBody.contactPerson() != null) {
      contactPerson =
          ContactPerson.builder()
              .salutation(requestBody.contactPerson().salutation())
              .firstName(requestBody.contactPerson().firstName())
              .lastName(requestBody.contactPerson().lastName())
              .build();
      contactPerson = contactPersonRepository.save(contactPerson);
    }

    B2bCustomer customer =
        B2bCustomer.builder()
            .companyName(requestBody.companyName())
            .taxId(requestBody.taxId())
            .vatIdentificationNumber(requestBody.vatIdentificationNumber())
            .city(requestBody.address().city())
            .street(requestBody.address().street())
            .zipCode(requestBody.address().zipCode())
            .email(requestBody.contactData().email())
            .phoneNumber(requestBody.contactData().phoneNumber())
            .customerId(CustomCustomerIdGenerator.generateB2bCustomerId())
            .contactPerson(contactPerson)
            .build();

    try {
      return b2bCustomerRepository.save(customer);
    } catch (DataIntegrityViolationException ex) {
      Throwable root = getRootCause(ex);
      if (root instanceof PSQLException psql && UNIQUE_VIOLATION_STATE.equals(psql.getSQLState())) {
        String msg = buildUniqueViolationMessage(psql);
        log.error("Create B2B customer failed: {}", msg);
        throw new IllegalArgumentException(msg, ex);
      }
      throw ex;
    }
  }

  /**
   * Edit the user via REST call. Only the field witch are changed will update.
   *
   * @param requestBody REST body witch contains the data
   * @return B2bCustomer even if we didnt change anything
   */
  public B2bCustomer editB2bCustomer(String customerId, B2bCustomerPayload requestBody) {

    B2bCustomer actualCustomer = getCustomer(customerId);

    final int actualCustomerHash = actualCustomer.hashCode();

    if (!actualCustomer
        .getVatIdentificationNumber()
        .equals(requestBody.vatIdentificationNumber())) {
      actualCustomer.setVatIdentificationNumber(requestBody.vatIdentificationNumber());
    }
    if (!actualCustomer.getTaxId().equals(requestBody.taxId())) {
      actualCustomer.setTaxId(requestBody.taxId());
    }
    if (!actualCustomer.getCompanyName().equals(requestBody.companyName())) {
      actualCustomer.setCompanyName(requestBody.companyName());
    }
    if (!actualCustomer.getStreet().equals(requestBody.address().street())) {
      actualCustomer.setStreet(requestBody.address().street());
    }
    if (!actualCustomer.getZipCode().equals(requestBody.address().zipCode())) {
      actualCustomer.setZipCode(requestBody.address().zipCode());
    }
    if (!actualCustomer.getEmail().equals(requestBody.contactData().email())) {
      actualCustomer.setEmail(requestBody.contactData().email());
    }
    if (!actualCustomer.getPhoneNumber().equals(requestBody.contactData().phoneNumber())) {
      actualCustomer.setPhoneNumber(requestBody.contactData().phoneNumber());
    }
    if (actualCustomer.getContactPerson() == null) {
      ContactPerson contactPerson =
          ContactPerson.builder() //
              .salutation(requestBody.contactPerson().salutation()) //
              .firstName(requestBody.contactPerson().firstName()) //
              .lastName(requestBody.contactPerson().lastName()) //
              .build();
      contactPerson = contactPersonRepository.save(contactPerson);
      actualCustomer.setContactPerson(contactPerson);
    }

    if (actualCustomer.hashCode() != actualCustomerHash) {
      actualCustomer = b2bCustomerRepository.save(actualCustomer);
    }
    return actualCustomer;
  }

  private String buildUniqueViolationMessage(PSQLException psql) {
    String constraint =
        psql.getServerErrorMessage() != null ? psql.getServerErrorMessage().getConstraint() : null;
    String detail =
        psql.getServerErrorMessage() != null ? psql.getServerErrorMessage().getDetail() : null;

    if (detail != null) {
      Matcher m = PG_KEY_DETAIL.matcher(detail);
      if (m.find()) {
        String[] cols = m.group(1).split(",\\s*");
        String[] vals = m.group(2).split(",\\s*");
        StringBuilder sb = new StringBuilder("Unique constraint violation: ");
        for (int i = 0; i < cols.length; i++) {
          if (i > 0) sb.append(", ");
          sb.append(toCamel(cols[i])).append(" '").append(vals[i]).append("' already exists");
        }
        return sb.toString();
      }
    }

    if (constraint != null) {
      return "Unique constraint violation (" + constraint + ")";
    }
    return "Unique constraint violation";
  }

  private String toCamel(String snake) {
    StringBuilder sb = new StringBuilder();
    boolean upper = false;
    for (char c : snake.toCharArray()) {
      if (c == '_') {
        upper = true;
      } else {
        sb.append(upper ? Character.toUpperCase(c) : c);
        upper = false;
      }
    }
    return sb.toString();
  }

  private Throwable getRootCause(Throwable t) {
    Throwable current = t;
    while (current.getCause() != null && current.getCause() != current) {
      current = current.getCause();
    }
    return current;
  }

  private void validateCustomerData(CreateB2bCustomerPayload payload) {
    if (payload == null) {
      throw new IllegalArgumentException("Payload must not be null");
    }
    if (isBlank(payload.companyName())) {
      fail("companyName must be provided");
    }
    if (isBlank(payload.taxId()) && isBlank(payload.vatIdentificationNumber())) {
      fail("Either taxId or vatIdentificationNumber must be provided");
    }
    if (!isBlank(payload.vatIdentificationNumber())
        && !VAT_PATTERN.matcher(payload.vatIdentificationNumber()).matches()) {
      fail("vatIdentificationNumber must match ^DE[0-9]{9}$");
    }
    if (!isBlank(payload.taxId())) {
      String cleaned = payload.taxId().replaceAll("\\D", "");
      if (!TAX_ID_PATTERN.matcher(cleaned).matches()) {
        fail("taxId must contain 10 or 11 digits (after normalization)");
      }
    }
    if (isBlank(payload.address().city())) {
      fail("city must be provided");
    }
    if (isBlank(payload.address().street())) {
      fail("street must be provided if address is present");
    }
    if (isBlank(payload.address().zipCode())) {
      fail("zipCode must be provided if address is present");
    }
    if (payload.contactData() != null) {
      if (isBlank(payload.contactData().email())) {
        fail("contactData.email must be provided if contactData is present");
      }
      if (isBlank(payload.contactData().phoneNumber())) {
        fail("contactData.phoneNumber must be provided if contactData is present");
      }
    }
    boolean hasFirstName = !isBlank(payload.contactPerson().firstName());
    boolean hasLastName = !isBlank(payload.contactPerson().lastName());
    boolean hasSalutation = !isBlank(payload.contactPerson().salutation());

    int filledFields = (hasFirstName ? 1 : 0) + (hasLastName ? 1 : 0) + (hasSalutation ? 1 : 0);

    if (filledFields > 0 && filledFields < 3) {
      fail(
          "All contactPerson fields (salutation, firstName, lastName) must be provided if any is"
              + " present.");
    }
  }

  private boolean isBlank(String s) {
    return s == null || s.trim().isEmpty();
  }

  private void fail(String message) {
    log.error("Validation of user creation request failed: {}", message);
    throw new IllegalArgumentException(message);
  }
}
