package com.sook.backend.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import lombok.Getter;

@Getter
public abstract class ApiException extends ResponseStatusException {

    private final String message;

    public ApiException(final HttpStatus status, final String message) {
        super(status, message);
        this.message = message;
    }
}