package com.sook.backend.user.exception;

import com.sook.backend.common.exception.NotFoundException;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException() {
        super("없는 유저입니다");
    }
}
