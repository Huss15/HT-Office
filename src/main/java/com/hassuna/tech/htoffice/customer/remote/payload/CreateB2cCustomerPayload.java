package com.hassuna.tech.htoffice.customer.remote.payload;

public record CreateB2cCustomerPayload(String firstname, String lastname, AddressPayload address,
                                       ContactDataPayload contactData) {

}
