package com.hassuna.tech.htoffice.customer.application;

import com.hassuna.tech.htoffice.customer.application.entity.B2bCustomer;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface B2bCustomerRepository extends CrudRepository<B2bCustomer, Long> {

    Optional<B2bCustomer> findByCustomId(String customId);
}
