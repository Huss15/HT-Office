package com.hassuna.tech.htoffice.base.application.error;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hassuna.tech.htoffice.base.application.error.entity.ErrorLog;

public interface ErrorLogRepository extends JpaRepository<ErrorLog, Long> {}
