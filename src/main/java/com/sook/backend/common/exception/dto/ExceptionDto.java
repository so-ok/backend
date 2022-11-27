package com.sook.backend.common.exception.dto;

import org.springframework.http.HttpStatus;

import com.sook.backend.common.exception.ApiException;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ExceptionDto {
    private final int status;
    private final String error;
    private final String message;

    @Builder(access = AccessLevel.PRIVATE)
    public ExceptionDto(HttpStatus httpStatus, String message) {
        this.status = httpStatus.value();
        this.error = httpStatus.toString();
        this.message = message;
    }

    public static ExceptionDto of(Exception exception) {
        return ExceptionDto.builder()
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(exception.getMessage())
                .build();
    }

    public static ExceptionDto of(ApiException exception) {
        return ExceptionDto.builder()
                .httpStatus(exception.getStatus())
                .message(exception.getMessage())
                .build();
    }
}