package com.hassuna.tech.htoffice.customer.application;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hassuna.tech.htoffice.customer.application.entity.Customer;
import com.hassuna.tech.htoffice.customer.remote.payload.CustomerDtoPayload;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

  @Query(
      value =
          "SELECT customer_id AS customerId, CONCAT(firstname, ' ', lastname) AS name, FALSE AS"
              + " isB2b FROM customer_b2c UNION ALL SELECT customer_id AS customerId, company_name"
              + " AS name, TRUE AS isB2b FROM customer_b2b",
      countQuery =
          "SELECT COUNT(*) FROM (SELECT customer_id FROM customer_b2c UNION ALL SELECT customer_id"
              + " FROM customer_b2b) AS all_customers",
      nativeQuery = true)
  Page<CustomerDtoPayload> findAllCustomers(Pageable pageable);

  @Query(
      value =
          "SELECT COUNT(*) FROM (SELECT customer_id FROM customer_b2c UNION ALL SELECT customer_id"
              + " FROM customer_b2b) AS all_customers",
      nativeQuery = true)
  int countAllCustomers();
}
