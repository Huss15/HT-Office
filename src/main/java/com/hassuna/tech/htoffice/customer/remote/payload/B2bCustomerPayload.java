package com.hassuna.tech.htoffice.customer.remote.payload;

import com.hassuna.tech.htoffice.base.application.MaskingUtils;
import com.hassuna.tech.htoffice.customer.application.entity.B2bCustomer;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "B2B customer response payload")
public record B2bCustomerPayload(
    @Schema(description = "Customer business identifier", example = "HT-C-000001")
        String customerId,
    @Schema(description = "Registered company name", example = "Acme GmbH") String companyName,
    @Schema(description = "Masked national tax ID", example = "*****123") String taxId,
    @Schema(description = "Masked VAT identification number", example = "*******789")
        String vatIdentificationNumber,
    @Schema(description = "Registered address") AddressPayload address,
    @Schema(description = "Primary contact data") ContactDataPayload contactData,
    @Schema(description = "Primary contact person") ContactPersonPayload contactPerson) {

  public static B2bCustomerPayload convertToB2bCustomerPayload(B2bCustomer customer) {
    return new B2bCustomerPayload(
        customer.getCustomerId(),
        customer.getCompanyName(),
        MaskingUtils.maskExceptLast3(customer.getTaxId()),
        MaskingUtils.maskExceptLast3(customer.getVatIdentificationNumber()),
        new AddressPayload(customer.getStreet(), customer.getCity(), customer.getZipCode()),
        new ContactDataPayload(customer.getEmail(), customer.getPhoneNumber()),
        new ContactPersonPayload(
            customer.getContactPerson().getSalutation(),
            customer.getContactPerson().getFirstName(),
            customer.getContactPerson().getLastName()));
  }
}
