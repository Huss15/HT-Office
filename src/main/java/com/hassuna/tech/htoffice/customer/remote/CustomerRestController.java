package com.hassuna.tech.htoffice.customer.remote;

import org.springframework.http.ResponseEntity;

import com.hassuna.tech.htoffice.customer.remote.payload.B2bCustomerPayload;
import com.hassuna.tech.htoffice.customer.remote.payload.B2cCustomerPayload;
import com.hassuna.tech.htoffice.customer.remote.payload.CreateB2bCustomerPayload;
import com.hassuna.tech.htoffice.customer.remote.payload.CreateB2cCustomerPayload;

public interface CustomerRestController {

  ResponseEntity<B2bCustomerPayload> createB2bCustomer(CreateB2bCustomerPayload requestBody);

  ResponseEntity<B2cCustomerPayload> createB2cCustomer(CreateB2cCustomerPayload requestBody);

  ResponseEntity<B2bCustomerPayload> getB2bCustomerByCustomerId(String customerId);

  ResponseEntity<B2cCustomerPayload> getB2cCustomerByCustomerId(String customerId);
}
