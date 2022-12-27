package com.sook.backend.security.auth.key;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNoException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.sook.backend.security.auth.exception.InvalidTokenException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

class AccessKeyTest {

    private static final String SECRET = "testaccesssecrettestaccesssecrettestaccesssecrettestaccesssecret";
    private static final Long DURATION = 86400000L;
    private static final String HS512_PREFIX = "eyJhbGciOiJIUzUxMiJ9";
    private static final String SUBJECT = "testsubject";

    private AccessSecret accessSecret;
    private String accessToken;

    @BeforeEach
    void setUp() {
        accessSecret = new AccessSecret(SECRET, DURATION);
        accessToken = accessSecret.issueTokenWith(buildClaims(SUBJECT));
    }

    @Test
    @DisplayName("Claims로 토큰을 발급한다")
    void generate_token_with_claims() {
        assertThat(accessToken).startsWith(HS512_PREFIX);
    }

    @Test
    @DisplayName("토큰에서 Claims를 가져온다")
    void parse() {
        assertThat(accessSecret.parse(accessToken).getSubject()).isEqualTo(SUBJECT);
    }

    @Test
    @DisplayName("정상 토큰은 예외를 던지지 않는다")
    void validate() {
        assertThatNoException().isThrownBy(() -> accessSecret.parse(accessToken));
    }

    @Test
    @DisplayName("비정상 토큰은 예외를 던진다")
    void validate_thrown() {
        assertThatExceptionOfType(InvalidTokenException.class).isThrownBy(() -> accessSecret.parse("INVALID"));
    }

    private Claims buildClaims(String subject) {
        Claims claims = Jwts.claims();
        claims.setSubject(subject);
        return claims;
    }
}