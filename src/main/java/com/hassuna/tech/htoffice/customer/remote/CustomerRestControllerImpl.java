package com.hassuna.tech.htoffice.customer.remote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hassuna.tech.htoffice.customer.application.B2bCustomerService;
import com.hassuna.tech.htoffice.customer.application.B2cCustomerService;
import com.hassuna.tech.htoffice.customer.application.entity.B2bCustomer;
import com.hassuna.tech.htoffice.customer.application.entity.B2cCustomer;
import com.hassuna.tech.htoffice.customer.remote.payload.B2bCustomerPayload;
import com.hassuna.tech.htoffice.customer.remote.payload.B2cCustomerPayload;
import com.hassuna.tech.htoffice.customer.remote.payload.CreateB2bCustomerPayload;
import com.hassuna.tech.htoffice.customer.remote.payload.CreateB2cCustomerPayload;

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
  @PostMapping("v1/customer/b2b")
  public ResponseEntity<B2bCustomerPayload> createB2bCustomer(
      @RequestBody CreateB2bCustomerPayload requestBody) {
    log.debug("Resieved request to create B2B customer: {}", requestBody.companyName());
    B2bCustomer customer = b2bCustomerService.createCustomer(requestBody);
    B2bCustomerPayload responsePayload = B2bCustomerPayload.convertToB2bCustomerPayload(customer);
    log.debug("Created B2B customer: {}", responsePayload.customId());
    return ResponseEntity.ok(responsePayload);
  }

  @Override
  @PostMapping("v1/customer/b2c")
  public ResponseEntity<B2cCustomerPayload> createB2cCustomer(
      CreateB2cCustomerPayload requestBody) {
    log.debug(
        "Received request to create B2C customer: {} {}",
        requestBody.firstname(),
        requestBody.lastname());
    B2cCustomer customer = b2cCustomerService.createCustomer(requestBody);
    B2cCustomerPayload responsePayload = B2cCustomerPayload.convertToB2cCustomerPayload(customer);
    log.debug("Created B2C customer: {}", responsePayload.customerId());
    return ResponseEntity.ok(responsePayload);
  }

  @Override
  @GetMapping("v1/customer/b2b/{customerId}")
  public ResponseEntity<B2bCustomerPayload> getB2bCustomerByCustomerId(
      @PathVariable String customerId) {
    log.debug("Received request to get B2B customer with ID: {}", customerId);
    B2bCustomer customer = b2bCustomerService.getCustomer(customerId);
    B2bCustomerPayload responsePayload = B2bCustomerPayload.convertToB2bCustomerPayload(customer);
    log.debug("Retrieved B2B customer: {}", responsePayload.customId());
    return ResponseEntity.ok(responsePayload);
  }

  @Override
  @GetMapping("v1/customer/b2c/{customerId}")
  public ResponseEntity<B2cCustomerPayload> getB2cCustomerByCustomerId(
      @PathVariable String customerId) {
    log.debug("Received request to get B2C customer with ID: {}", customerId);
    B2cCustomer customer = b2cCustomerService.getCustomer(customerId);
    B2cCustomerPayload responsePayload = B2cCustomerPayload.convertToB2cCustomerPayload(customer);
    log.debug("Retrieved B2C customer: {}", responsePayload.customerId());
    return ResponseEntity.ok(responsePayload);
  }
}
