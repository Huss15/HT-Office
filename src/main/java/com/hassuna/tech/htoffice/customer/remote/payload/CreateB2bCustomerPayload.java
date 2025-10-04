package com.hassuna.tech.htoffice.customer.remote.payload;

public record CreateB2bCustomerPayload(String companyName, String taxId, String VatIdentificationNumber,
                                       AddressPayload address, ContactDataPayload contactData, ContactPersonPayload contactPerson) {
}

