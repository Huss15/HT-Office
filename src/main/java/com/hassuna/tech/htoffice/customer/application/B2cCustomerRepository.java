package com.hassuna.tech.htoffice.customer.application;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.hassuna.tech.htoffice.customer.application.entity.B2cCustomer;

public interface B2cCustomerRepository extends CrudRepository<B2cCustomer, Long> {

  Optional<B2cCustomer> findByCustomId(String customId);
}
