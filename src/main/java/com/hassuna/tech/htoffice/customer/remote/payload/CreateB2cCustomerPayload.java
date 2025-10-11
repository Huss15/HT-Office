package com.hassuna.tech.htoffice.customer.remote.payload;

public record CreateB2cCustomerPayload(
    String firstName,
    String lastName,
    String salutation,
    AddressPayload address,
    ContactDataPayload contactData) {}
