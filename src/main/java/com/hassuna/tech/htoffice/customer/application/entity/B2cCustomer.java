package com.hassuna.tech.htoffice.customer.application.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
@Entity
@Table(name = "customer_b2c")
public class B2cCustomer extends Customer {

  private String firstname;
  private String lastname;
  private String salutation;

  public B2cCustomer() {
    super();
  }
}
