package com.sook.backend.security.auth.key;

import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class Key {

    private final Long duration;
    private final Secret secret;

    public Key(Secret secret, Long duration) {
        this.secret = secret;
        this.duration = duration;
    }

    public String issueTokenWith(Claims claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now())
                .setExpiration(getExpirationDate())
                .signWith(secret.asKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    public Claims parse(String token) {
        return secret.parser()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isValid(String token) {
        try {
            parse(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            log.warn(e.getMessage());
            return false;
        }
    }

    private Date getExpirationDate() {
        return new Date(now().getTime() + duration);
    }

    private Date now() {
        return new Date();
    }
}
