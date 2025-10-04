package com.hassuna.tech.htoffice.customer.application;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.postgresql.util.PSQLException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.hassuna.tech.htoffice.customer.application.entity.B2bCustomer;
import com.hassuna.tech.htoffice.customer.remote.payload.CreateB2bCustomerPayload;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class B2bCustomerService {

  private static final Pattern VAT_PATTERN = Pattern.compile("^DE\\d{9}$");
  private static final Pattern TAX_ID_PATTERN = Pattern.compile("^\\d{10,11}$");
  private static final String UNIQUE_VIOLATION_STATE = "23505";
  private static final Pattern PG_KEY_DETAIL =
      Pattern.compile("Key \\(([^)]+)\\)=\\(([^)]+)\\) already exists\\.");

  private final B2bCustomerRepository b2bCustomerRepository;

  public B2bCustomerService(B2bCustomerRepository b2bCustomerRepository) {
    this.b2bCustomerRepository = b2bCustomerRepository;
  }

  public B2bCustomer getCustomer(final String customerId) {
    CustomCustomerIdGenerator.assertValidCustomerId(customerId);
    return b2bCustomerRepository
        .findByCustomId(customerId)
        .orElseThrow(
            () -> {
              log.warn("Customer with ID {} not found", customerId);
              return new IllegalArgumentException("Customer not found with ID: " + customerId);
            });
  }

  public B2bCustomer createCustomer(CreateB2bCustomerPayload requestBody)
      throws IllegalArgumentException {

    validateCustomerData(requestBody);

    B2bCustomer customer =
        B2bCustomer.builder()
            .companyName(requestBody.companyName())
            .taxId(requestBody.taxId())
            .VatIdentificationNumber(requestBody.VatIdentificationNumber())
            .city(requestBody.address().city())
            .street(requestBody.address().street())
            .zipCode(requestBody.address().zipCode())
            .email(requestBody.contactData().email())
            .phoneNumber(requestBody.contactData().phoneNumber())
            .customId(CustomCustomerIdGenerator.generateB2bCustomerId())
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
    if (isBlank(payload.taxId()) && isBlank(payload.VatIdentificationNumber())) {
      fail("Either taxId or VatIdentificationNumber must be provided");
    }
    if (!isBlank(payload.VatIdentificationNumber())
        && !VAT_PATTERN.matcher(payload.VatIdentificationNumber()).matches()) {
      fail("VatIdentificationNumber must match ^DE[0-9]{9}$");
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
    if (payload.contactPerson() != null) {
      if (isBlank(payload.contactPerson().firstName())) {
        fail("contactPerson.firstName must be provided if contactPerson is present.");
      }
      if (isBlank(payload.contactPerson().lastName())) {
        fail("contactPerson.lastName must be provided if contactPerson is present.");
      }
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
