package com.hassuna.tech.htoffice.base.application.error;

import com.hassuna.tech.htoffice.base.application.error.entity.ErrorLog;
import org.springframework.data.repository.CrudRepository;

public interface ErrorLogRepository extends CrudRepository<ErrorLog, Long> {
}
