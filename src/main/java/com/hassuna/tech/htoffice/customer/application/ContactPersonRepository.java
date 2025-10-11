package com.hassuna.tech.htoffice.customer.application;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hassuna.tech.htoffice.customer.application.entity.ContactPerson;

public interface ContactPersonRepository extends JpaRepository<ContactPerson, Long> {}
