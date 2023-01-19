package com.sook.backend.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.sook.backend.common.dto.ExceptionDto;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ExceptionDto> handleException(final Exception exception) {
        log.error("INTERNAL_SERVER_ERROR: {}", exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ExceptionDto.of(exception));
    }

    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<ExceptionDto> handleSecurityException(final AccessDeniedException exception) {
        log.error("SECURITY_EXCEPTION: {}", exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(ExceptionDto.of(exception));
    }

    @ExceptionHandler(ApiException.class)
    protected ResponseEntity<ExceptionDto> handleApiException(final ApiException exception) {
        log.error("API_EXCEPTION: {}", exception.getMessage());
        return ResponseEntity
                .status(exception.getStatus())
                .body(ExceptionDto.of(exception));
    }
}
