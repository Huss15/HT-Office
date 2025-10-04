package com.hassuna.tech.htoffice.customer.application;

import com.hassuna.tech.htoffice.customer.application.entity.B2bCustomer;
import com.hassuna.tech.htoffice.customer.remote.payload.CreateB2bCustomerPayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
@Slf4j
public class B2bCustomerService {

    private static final Pattern VAT_PATTERN = Pattern.compile("^DE\\d{9}$");
    private static final Pattern TAX_ID_PATTERN = Pattern.compile("^\\d{10,11}$");

    private final B2bCustomerRepository b2bCustomerRepository;

    public B2bCustomerService(B2bCustomerRepository b2bCustomerRepository) {
        this.b2bCustomerRepository = b2bCustomerRepository;
    }

    /**
     * Retrieves a B2B customer by their custom ID.
     *
     * @param customerId the custom ID of the customer
     * @return B2bCustomer
     * @throws IllegalArgumentException if the ID format is invalid or customer not found
     */
    public B2bCustomer getCustomer(final String customerId) {

        CustomCustomerIdGenerator.assertValidCustomerId(customerId);

        return b2bCustomerRepository.findByCustomId(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer with ID " + customerId + " not found."));
    }

    /**
     * Creates a new B2B customer after validating the input data.
     *
     * @param requestBody the payload containing customer data
     * @return B2bCustomer
     * @throws IllegalArgumentException if validation fails
     */
    public B2bCustomer createCustomer(CreateB2bCustomerPayload requestBody) throws IllegalArgumentException {

        validateCustomerData(requestBody);

        B2bCustomer customer = B2bCustomer.builder() //
                .companyName(requestBody.companyName()) //
                .taxId(requestBody.taxId()) //
                .VatIdentificationNumber(requestBody.VatIdentificationNumber()) //
                .city(requestBody.address().city())
                .street(requestBody.address().street())
                .zipCode(requestBody.address().zipCode())
                .email(requestBody.contactData().email())
                .phoneNumber(requestBody.contactData().phoneNumber())
                .customId(CustomCustomerIdGenerator.generateCustomerId())
                .build();

        return b2bCustomerRepository.save(customer);
    }

    private void validateCustomerData(CreateB2bCustomerPayload payload) {
        if (payload == null) {
            throw new IllegalArgumentException("Payload must not be null.");
        }

        // Company name
        if (isBlank(payload.companyName())) {
            fail("companyName must be provided");
        }

        // Tax identifiers (at least one)
        if (isBlank(payload.taxId()) && isBlank(payload.VatIdentificationNumber())) {
            fail("Either taxId or VatIdentificationNumber must be provided");
        }

        // VAT number format
        if (!isBlank(payload.VatIdentificationNumber()) &&
                !VAT_PATTERN.matcher(payload.VatIdentificationNumber()).matches()) {
            fail("VatIdentificationNumber must match ^DE[0-9]{9}$");
        }

        // Tax ID format (normalized)
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

        // Contact data (optional)
        if (payload.contactData() != null) {
            if (isBlank(payload.contactData().email())) {
                fail("contactData.email must be provided if contactData is present");
            }
            if (isBlank(payload.contactData().phoneNumber())) {
                fail("contactData.phoneNumber must be provided if contactData is present");
            }
        }

        // Contact person (optional but consistent)
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
