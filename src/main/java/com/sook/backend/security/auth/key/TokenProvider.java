package com.sook.backend.security.auth.key;

import static io.jsonwebtoken.SignatureAlgorithm.HS512;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

import com.sook.backend.security.auth.exception.InvalidTokenException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class TokenProvider {
    private final Long duration;
    private final Key key;
    private final JwtParser parser;

    public TokenProvider(String secret, Long duration) {
        this.key = Keys.hmacShaKeyFor(encode(secret));
        this.parser = Jwts.parserBuilder()
                .setSigningKey(key)
                .build();
        this.duration = duration;
    }

    public String issueWith(Claims claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now())
                .setExpiration(expirationDate())
                .signWith(key, HS512)
                .compact();
    }

    public Claims parse(String token) {
        validateToken(token);
        return parser
                .parseClaimsJws(token)
                .getBody();
    }

    private void validateToken(String token) {
        try {
            parser.parseClaimsJws(token);
        } catch (JwtException | IllegalArgumentException e) {
            throw new InvalidTokenException();
        }
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
