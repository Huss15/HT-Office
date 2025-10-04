package com.hassuna.tech.htoffice.customer.remote.payload;

import com.hassuna.tech.htoffice.customer.application.entity.B2cCustomer;

public record B2cCustomerPayload(
    String customerId,
    String firstname,
    String lastname,
    AddressPayload address,
    ContactDataPayload contactData) {

  public static B2cCustomerPayload convertToB2cCustomerPayload(B2cCustomer customer) {
    return new B2cCustomerPayload(
        customer.getCustomerId(),
        customer.getFirstname(),
        customer.getLastname(),
        new AddressPayload(customer.getStreet(), customer.getCity(), customer.getZipCode()),
        new ContactDataPayload(customer.getEmail(), customer.getPhoneNumber()));
  }
}
