package com.hassuna.tech.htoffice.customer.application;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hassuna.tech.htoffice.customer.application.entity.B2bCustomer;

public interface B2bCustomerRepository extends JpaRepository<B2bCustomer, Long> {

  Optional<B2bCustomer> findByCustomerId(String customerId);
}
