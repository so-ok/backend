package com.sook.backend.security.auth.key;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

class AccessKeyTest {

    private static final String SECRET = "testaccesssecrettestaccesssecrettestaccesssecrettestaccesssecret";
    private static final Long DURATION = 86400000L;
    private static final String HS512_PREFIX = "eyJhbGciOiJIUzUxMiJ9";
    private static final String SUBJECT = "testsubject";

    private AccessKey accessKey;
    private String accessToken;

    @BeforeEach
    void setUp() {
        accessKey = new AccessKey(SECRET, DURATION);
        accessToken = accessKey.issueTokenWith(buildClaims(SUBJECT));
    }

    @Test
    @DisplayName("Claims로 토큰을 발급한다")
    void generate_token_with_claims() {
        assertThat(accessToken).startsWith(HS512_PREFIX);
    }

    @Test
    @DisplayName("토큰에서 Claims를 가져온다")
    void parse() {

        assertThat(accessKey.parse(accessToken).getSubject()).isEqualTo(SUBJECT);
    }

    @Test
    @DisplayName("토큰이 유효한지 판별한다")
    void validate() {
        assertThat(accessToken)
                .satisfies(token -> assertThat(accessKey.checkValidityOf(token)).isTrue());
    }

    private Claims buildClaims(String subject) {
        Claims claims = Jwts.claims();
        claims.setSubject(subject);
        return claims;
    }
}