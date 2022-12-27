package com.sook.backend.security.auth.key;

import static io.jsonwebtoken.SignatureAlgorithm.HS512;

import java.util.Base64;
import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class Key {
    private final Long duration;
    private final String secret;

    public Key(String secret, Long duration) {
        this.secret = secret;
        this.duration = duration;
    }

    public String issueTokenWith(Claims claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now())
                .setExpiration(expirationDate())
                .signWith(key(), HS512)
                .compact();
    }

    public Claims parse(String token) {
        return parser()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean checkValidityOf(String token) {
        try {
            parse(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private java.security.Key key() {
        return Keys.hmacShaKeyFor(encode(secret));
    }

    private JwtParser parser() {
        return Jwts.parserBuilder()
                .setSigningKey(key())
                .build();
    }

    private byte[] encode(String secret) {
        return Base64.getEncoder()
                .encode(secret.getBytes());
    }

    private Date expirationDate() {
        return new Date(now().getTime() + duration);
    }

    private Date now() {
        return new Date();
    }
}
