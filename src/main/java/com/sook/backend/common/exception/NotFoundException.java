package com.sook.backend.common.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public abstract class NotFoundException extends ApiException {
    public NotFoundException(String message) {
        super(NOT_FOUND, message);
    }
}
