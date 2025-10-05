package com.hassuna.tech.htoffice.customer.application;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hassuna.tech.htoffice.customer.application.entity.B2cCustomer;

public interface B2cCustomerRepository extends JpaRepository<B2cCustomer, Long> {

  Optional<B2cCustomer> findByCustomerId(String customId);
}
