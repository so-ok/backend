package com.sook.backend.security.auth.key;

import java.util.Objects;
import java.util.function.Supplier;

import com.sook.backend.security.auth.exception.InvalidTokenException;

import io.jsonwebtoken.Claims;

public class Token {

    private final String token;
    private final Key key;

    public Token(String token) {
        this.token = token;
        this.key = null;
    }

    private Token(String token, Key key) {
        validateNotNull(key);
        validateToken(token, key);
        this.key = key;
        this.token = token;
    }

    public Token withKey(Key key) {
        return new Token(token, key);
    }

    private void validateNotNull(Key key) {
        if (Objects.isNull(key)) {
            throw new InvalidTokenException();
        }
    }

    private void validateToken(String token, Key key) {
        if (!key.checkValidityOf(token)) {
            throw new InvalidTokenException();
        }
    }

    public Claims claims() {
        return doWithKey(() -> key.parse(token));
    }

    private <T> T doWithKey(Supplier<T> supplier) {
        validateNotNull(key);
        return supplier.get();
    }
}
