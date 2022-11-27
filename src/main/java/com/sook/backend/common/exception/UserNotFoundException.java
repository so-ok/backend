package com.sook.backend.common.exception;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException() {
        super("없는 유저입니다");
    }
}
