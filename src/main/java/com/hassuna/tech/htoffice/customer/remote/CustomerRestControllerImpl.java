package com.hassuna.tech.htoffice.customer.remote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hassuna.tech.htoffice.customer.application.B2bCustomerService;
import com.hassuna.tech.htoffice.customer.application.B2cCustomerService;
import com.hassuna.tech.htoffice.customer.application.CustomCustomerIdGenerator;
import com.hassuna.tech.htoffice.customer.application.entity.B2bCustomer;
import com.hassuna.tech.htoffice.customer.application.entity.B2cCustomer;
import com.hassuna.tech.htoffice.customer.remote.payload.B2bCustomerPayload;
import com.hassuna.tech.htoffice.customer.remote.payload.B2cCustomerPayload;
import com.hassuna.tech.htoffice.customer.remote.payload.CreateB2bCustomerPayload;
import com.hassuna.tech.htoffice.customer.remote.payload.CreateB2cCustomerPayload;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api")
public class CustomerRestControllerImpl implements CustomerRestController {

  private final B2bCustomerService b2bCustomerService;
  private final B2cCustomerService b2cCustomerService;

  @Autowired
  public CustomerRestControllerImpl(
      B2bCustomerService b2bCustomerService, B2cCustomerService b2cCustomerService) {
    this.b2bCustomerService = b2bCustomerService;
    this.b2cCustomerService = b2cCustomerService;
  }

  @Override
  @GetMapping("v1/customer/{customerId}")
  public ResponseEntity<?> getCustomerByCustomerId(
      @Parameter(description = "Customer custom ID, e.g. HT-B-000001 or HT-C-000001") @PathVariable
          String customerId) {

    if (CustomCustomerIdGenerator.isValidB2bCustomerId(customerId)) {
      B2bCustomer customer = b2bCustomerService.getCustomer(customerId);
      B2bCustomerPayload responsePayload = B2bCustomerPayload.convertToB2bCustomerPayload(customer);
      return ResponseEntity.ok(responsePayload);
    } else {
      B2cCustomer customer = b2cCustomerService.getCustomer(customerId);
      B2cCustomerPayload responsePayload = B2cCustomerPayload.convertToB2cCustomerPayload(customer);
      return ResponseEntity.ok(responsePayload);
    }
  }

  @Override
  @PostMapping("v1/customer/b2b")
  public ResponseEntity<B2bCustomerPayload> createB2bCustomer(
      @RequestBody CreateB2bCustomerPayload requestBody) {
    B2bCustomer customer = b2bCustomerService.createCustomer(requestBody);
    return ResponseEntity.ok(B2bCustomerPayload.convertToB2bCustomerPayload(customer));
  }

  @Override
  @PostMapping("v1/customer/b2c")
  public ResponseEntity<B2cCustomerPayload> createB2cCustomer(
      @RequestBody CreateB2cCustomerPayload requestBody) {
    B2cCustomer customer = b2cCustomerService.createCustomer(requestBody);
    return ResponseEntity.ok(B2cCustomerPayload.convertToB2cCustomerPayload(customer));
  }
}
