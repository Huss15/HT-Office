package com.hassuna.tech.htoffice.customer.application.entity;

import com.hassuna.tech.htoffice.base.application.security.AesGcmAttributeConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
@Entity
@Table(name = "customer_b2b")
public class B2bCustomer extends Customer {

  @Column(unique = true)
  private String companyName;

  @Column(unique = true)
  @Convert(converter = AesGcmAttributeConverter.class)
  private String taxId;

  @Column(unique = true)
  @Convert(converter = AesGcmAttributeConverter.class)
  private String VatIdentificationNumber;

  public B2bCustomer() {
    super();
  }
}
