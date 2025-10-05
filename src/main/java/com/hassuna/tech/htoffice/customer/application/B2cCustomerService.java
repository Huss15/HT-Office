package com.hassuna.tech.htoffice.customer.application;

import org.springframework.stereotype.Service;

import com.hassuna.tech.htoffice.customer.application.entity.B2cCustomer;
import com.hassuna.tech.htoffice.customer.remote.payload.CreateB2cCustomerPayload;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class B2cCustomerService {

  private final B2cCustomerRepository b2cCustomerRepository;

  public B2cCustomerService(B2cCustomerRepository b2cCustomerRepository) {
    this.b2cCustomerRepository = b2cCustomerRepository;
  }

  public B2cCustomer getCustomer(final String customerId) {
    CustomCustomerIdGenerator.assertValidCustomerId(customerId);
    return b2cCustomerRepository
        .findByCustomerId(customerId)
        .orElseThrow(
            () -> {
              log.warn("Customer with ID {} not found", customerId);
              return new IllegalArgumentException("Customer not found with ID: " + customerId);
            });
  }

  public B2cCustomer createCustomer(CreateB2cCustomerPayload requestBody) {
    validateCustomerData(requestBody);
    B2cCustomer customer =
        B2cCustomer.builder()
            .firstname(requestBody.firstname())
            .lastname(requestBody.lastname())
            .isMale(requestBody.isMale())
            .city(requestBody.address().city())
            .street(requestBody.address().street())
            .zipCode(requestBody.address().zipCode())
            .email(requestBody.contactData().email())
            .phoneNumber(requestBody.contactData().phoneNumber())
            .customerId(CustomCustomerIdGenerator.generateB2cCustomerId())
            .build();

    return b2cCustomerRepository.save(customer);
  }

  private void validateCustomerData(CreateB2cCustomerPayload requestBody) {
    if (isBlank(requestBody.firstname())) {
      fail("First name is required.");
    }
    if (isBlank(requestBody.lastname())) {
      fail("Last name is required.");
    }
    if (isBlank(requestBody.contactData().email())) {
      fail("Email is required.");
    }
    if (isBlank(requestBody.contactData().phoneNumber())) {
      fail("Phone number is required.");
    }
    if (isBlank(requestBody.address().city())) {
      fail("City is required.");
    }
    if (isBlank(requestBody.address().street())) {
      fail("Street is required.");
    }
    if (isBlank(requestBody.address().zipCode())) {
      fail("Zip code is required.");
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
