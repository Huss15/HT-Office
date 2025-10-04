package com.hassuna.tech.htoffice.customer.application;

import com.hassuna.tech.htoffice.customer.application.entity.B2cCustomer;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface B2cCustomerRepository extends CrudRepository<B2cCustomer, Long> {

    Optional<B2cCustomer> findByCustomId(String customId);
}
