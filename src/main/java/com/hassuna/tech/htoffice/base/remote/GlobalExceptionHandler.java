package com.hassuna.tech.htoffice.base.remote;


import com.hassuna.tech.htoffice.base.application.error.GlobalErrorService;
import com.hassuna.tech.htoffice.base.application.error.entity.ErrorLog;
import com.hassuna.tech.htoffice.base.remote.paylaod.ErrorPayload;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final GlobalErrorService errorService;

    public GlobalExceptionHandler(GlobalErrorService errorService) {
        this.errorService = errorService;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorPayload> handleIllegalArgument(IllegalArgumentException ex,
                                                              HttpServletRequest request) {

        ErrorLog error = errorService.logError(ex.getMessage(), request.getRequestURI(), ex.getClass().getName());
        ErrorPayload body = new ErrorPayload(
                error.getTimestamp().toString(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                error.getErrorMessage(),
                error.getHttpPath()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

}
