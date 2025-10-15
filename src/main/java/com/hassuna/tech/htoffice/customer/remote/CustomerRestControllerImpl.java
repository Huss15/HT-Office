package com.hassuna.tech.htoffice.customer.remote;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hassuna.tech.htoffice.base.remote.paylaod.PagePayload;
import com.hassuna.tech.htoffice.base.remote.paylaod.SimplePayload;
import com.hassuna.tech.htoffice.customer.application.B2bCustomerService;
import com.hassuna.tech.htoffice.customer.application.B2cCustomerService;
import com.hassuna.tech.htoffice.customer.application.CustomCustomerIdGenerator;
import com.hassuna.tech.htoffice.customer.application.CustomerService;
import com.hassuna.tech.htoffice.customer.application.entity.B2bCustomer;
import com.hassuna.tech.htoffice.customer.application.entity.B2cCustomer;
import com.hassuna.tech.htoffice.customer.remote.payload.B2bCustomerPayload;
import com.hassuna.tech.htoffice.customer.remote.payload.B2cCustomerPayload;
import com.hassuna.tech.htoffice.customer.remote.payload.CreateB2bCustomerPayload;
import com.hassuna.tech.htoffice.customer.remote.payload.CreateB2cCustomerPayload;
import com.hassuna.tech.htoffice.customer.remote.payload.CustomerDtoPayload;
import com.hassuna.tech.htoffice.customer.remote.payload.GetCustomerPayload;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api")
public class CustomerRestControllerImpl implements CustomerRestController {

  private final CustomerService customerService;
  private final B2bCustomerService b2bCustomerService;
  private final B2cCustomerService b2cCustomerService;

  public CustomerRestControllerImpl(
      CustomerService customerService,
      B2bCustomerService b2bCustomerService,
      B2cCustomerService b2cCustomerService) {
    this.customerService = customerService;
    this.b2bCustomerService = b2bCustomerService;
    this.b2cCustomerService = b2cCustomerService;
  }

  @Override
  @GetMapping("v1/customers")
  public PagePayload<CustomerDtoPayload> getAllCustomers(
      @PageableDefault(size = 20, sort = "name", direction = Sort.Direction.ASC)
          Pageable pageable) {
    Page<CustomerDtoPayload> b2bCustomers = customerService.getAllCustomers(pageable);

    return new PagePayload<>(b2bCustomers.getContent(), b2bCustomers.isLast());
  }

  @Override
  @GetMapping("v1/customers/total")
  public ResponseEntity<SimplePayload> getTotalCustomerCount() {
    int countResult = customerService.getTotalCustomerCount();
    SimplePayload responsePayload = new SimplePayload(countResult);
    return ResponseEntity.ok(responsePayload);
  }

  @Override
  @GetMapping("v1/customer/{customerId}")
  public ResponseEntity<GetCustomerPayload> getCustomerByCustomerId(
      @Parameter(description = "Customer custom ID, e.g. HT-B-000001 or HT-C-000001") @PathVariable
          String customerId) {

    if (CustomCustomerIdGenerator.isValidB2bCustomerId(customerId)) {
      B2bCustomer customer = b2bCustomerService.getCustomer(customerId);
      B2bCustomerPayload responsePayload = B2bCustomerPayload.convertToB2bCustomerPayload(customer);
      return ResponseEntity.ok(new GetCustomerPayload(true, responsePayload));
    } else {
      B2cCustomer customer = b2cCustomerService.getCustomer(customerId);
      B2cCustomerPayload responsePayload = B2cCustomerPayload.convertToB2cCustomerPayload(customer);
      return ResponseEntity.ok(new GetCustomerPayload(false, responsePayload));
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

  @Override
  @PutMapping("/v1/customer/{customerId}")
  public ResponseEntity<B2bCustomerPayload> editB2bCustomer(
      @PathVariable String customerId, @RequestBody B2bCustomerPayload requestBody) {
    B2bCustomer customer = b2bCustomerService.editB2bCustomer(customerId, requestBody);
    return ResponseEntity.ok(B2bCustomerPayload.convertToB2bCustomerPayload(customer));
  }
}
