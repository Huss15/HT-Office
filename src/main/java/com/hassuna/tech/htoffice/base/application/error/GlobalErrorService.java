package com.hassuna.tech.htoffice.base.application.error;

import org.springframework.stereotype.Service;

import com.hassuna.tech.htoffice.base.application.error.entity.ErrorLog;

@Service
public class GlobalErrorService {

  private final ErrorLogRepository errorLogRepository;

  public GlobalErrorService(ErrorLogRepository errorLogRepository) {
    this.errorLogRepository = errorLogRepository;
  }

  public ErrorLog logError(String message, String path, String exceptionType) {
    ErrorLog error =
        ErrorLog.builder()
            .errorMessage(message)
            .httpPath(path)
            .exceptionType(exceptionType)
            .build();
    return errorLogRepository.save(error);
  }
}
