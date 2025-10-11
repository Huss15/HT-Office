package com.hassuna.tech.htoffice.customer.remote.payload;

public record CreateB2cCustomerPayload(
    String firstname,
    String lastname,
    String salutation,
    AddressPayload address,
    ContactDataPayload contactData) {}
