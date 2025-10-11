package com.hassuna.tech.htoffice.customer.application.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Builder
@Setter
@Getter
@AllArgsConstructor
public class ContactPerson {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  private String salutation;
  private String firstName;
  private String lastName;

  public ContactPerson() {}
}
