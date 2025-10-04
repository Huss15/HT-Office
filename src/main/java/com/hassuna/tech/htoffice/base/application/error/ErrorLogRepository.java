package com.hassuna.tech.htoffice.base.application.error;

import org.springframework.data.repository.CrudRepository;

import com.hassuna.tech.htoffice.base.application.error.entity.ErrorLog;

public interface ErrorLogRepository extends CrudRepository<ErrorLog, Long> {}
