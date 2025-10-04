// src/main/java/com/hassuna/tech/htoffice/base/application/error/entity/ErrorLog.java
package com.hassuna.tech.htoffice.base.application.error.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "error_log")
@Getter
@Setter
@NoArgsConstructor
public class ErrorLog {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  private String errorMessage;
  private String httpPath;
  private String exceptionType;
  private LocalDateTime timestamp;

  @Builder
  public ErrorLog(
      String errorMessage, String httpPath, String exceptionType, LocalDateTime timestamp) {
    this.errorMessage = errorMessage;
    this.httpPath = httpPath;
    this.exceptionType = exceptionType;
    this.timestamp = timestamp == null ? LocalDateTime.now() : timestamp;
  }
}
