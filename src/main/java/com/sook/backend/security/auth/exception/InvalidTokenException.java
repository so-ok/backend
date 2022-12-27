package com.sook.backend.security.auth.exception;

import com.sook.backend.common.exception.BadRequestException;

public class InvalidTokenException extends BadRequestException {
    public InvalidTokenException() {
        super("잘못된 토큰입니다");
    }
}
