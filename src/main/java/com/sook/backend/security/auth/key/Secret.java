package com.sook.backend.security.auth.key;

import java.security.Key;
import java.util.Base64;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class Secret {

    private final String secret;

    public Secret(String secret) {
        this.secret = secret;
    }

    public Key asKey() {
        return Keys.hmacShaKeyFor(encode(secret));
    }

    public JwtParser parser() {
        return Jwts.parserBuilder()
                .setSigningKey(this.asKey())
                .build();
    }

    private byte[] encode(String secret) {
        return Base64.getEncoder()
                .encode(secret.getBytes());
    }
}
