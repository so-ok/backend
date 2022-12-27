package com.sook.backend.common.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public abstract class BadRequestException extends ApiException {
    public BadRequestException(String message) {
        super(BAD_REQUEST, message);
    }
}
