package com.hassuna.tech.htoffice.customer.application;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.hassuna.tech.htoffice.customer.remote.payload.CustomerDtoPayload;

@Service
public class CustomerService {

  private final CustomerRepository customerRepository;

  public CustomerService(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  public Page<CustomerDtoPayload> getAllCustomers(Pageable pageable) {
    return customerRepository.findAllCustomers(pageable);
  }

  public int getTotalCustomerCount() {
    return customerRepository.countAllCustomers();
  }
}
