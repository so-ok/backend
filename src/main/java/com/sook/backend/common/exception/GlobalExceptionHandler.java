package com.sook.backend.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.sook.backend.common.dto.ExceptionDto;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ExceptionDto> handleException(final Exception exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ExceptionDto.of(exception));
    }

    @ExceptionHandler(ApiException.class)
    protected ResponseEntity<ExceptionDto> handleApiException(final ApiException exception) {
        return ResponseEntity
                .status(exception.getStatus())
                .body(ExceptionDto.of(exception));
    }
}
