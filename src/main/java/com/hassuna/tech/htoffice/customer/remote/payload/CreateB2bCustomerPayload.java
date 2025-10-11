package com.hassuna.tech.htoffice.customer.remote.payload;

public record CreateB2bCustomerPayload(
    String companyName,
    String taxId,
    String vatIdentificationNumber,
    AddressPayload address,
    ContactDataPayload contactData,
    ContactPersonPayload contactPerson) {}
