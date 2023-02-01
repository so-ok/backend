package com.sook.backend.user.exception;

import com.sook.backend.common.exception.BadRequestException;

public class DuplicateEmailException extends BadRequestException {

    public DuplicateEmailException() {
        super("이미 존재하는 이메일입니다");
    }
}
