package com.hassuna.tech.htoffice.customer.application.entity;

import com.hassuna.tech.htoffice.base.application.security.AesGcmAttributeConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "customer_b2b")
public class B2bCustomer extends Customer {

  @Column(unique = true)
  private String companyName;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "contact_person_id")
  private ContactPerson contactPerson;

  @Column(unique = true)
  @Convert(converter = AesGcmAttributeConverter.class)
  private String taxId;

  @Column(unique = true)
  @Convert(converter = AesGcmAttributeConverter.class)
  private String vatIdentificationNumber;

  public B2bCustomer() {
    super();
  }
}
