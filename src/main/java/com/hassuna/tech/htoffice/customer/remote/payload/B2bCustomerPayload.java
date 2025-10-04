package com.hassuna.tech.htoffice.customer.remote.payload;

import com.hassuna.tech.htoffice.base.application.MaskingUtils;
import com.hassuna.tech.htoffice.customer.application.entity.B2bCustomer;

public record B2bCustomerPayload(
    String customId,
    String companyName,
    String taxId,
    String VatIdentificationNumber,
    AddressPayload address,
    ContactDataPayload contactData) {

  public static B2bCustomerPayload convertToB2bCustomerPayload(B2bCustomer customer) {
    return new B2bCustomerPayload(
        customer.getCustomId(),
        customer.getCompanyName(),
        MaskingUtils.maskExceptLast3(customer.getTaxId()),
        MaskingUtils.maskExceptLast3(customer.getVatIdentificationNumber()),
        new AddressPayload(customer.getStreet(), customer.getCity(), customer.getZipCode()),
        new ContactDataPayload(customer.getEmail(), customer.getPhoneNumber()));
  }
}
