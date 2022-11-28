package com.sook.backend.common.dto;

import org.springframework.http.HttpStatus;

import com.sook.backend.common.exception.ApiException;

public record ExceptionDto(
        int status,
        String error,
        String message
) {

    public static ExceptionDto of(Exception exception) {
        return new ExceptionDto(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                exception.getMessage()
        );
    }

    public static ExceptionDto of(ApiException exception) {
        return new ExceptionDto(
                exception.getStatus().value(),
                exception.getStatus().toString(),
                exception.getMessage()
        );
    }
}
