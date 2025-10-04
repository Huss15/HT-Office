package com.hassuna.tech.htoffice.customer.application;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.hassuna.tech.htoffice.customer.application.entity.B2bCustomer;

public interface B2bCustomerRepository extends CrudRepository<B2bCustomer, Long> {

  Optional<B2bCustomer> findByCustomId(String customId);
}
