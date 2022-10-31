package com.sook.backend.security.auth.key;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

class AccessKeyTest {

	private static final String ACCESS_TOKEN_SECRET = "testaccesssecrettestaccesssecrettestaccesssecrettestaccesssecret";
	private static final Long ACCESS_TOKEN_EXPIRY = 86400000L;
	private static final String JWT_PREFIX_HS512 = "eyJhbGciOiJIUzUxMiJ9";

	private static final JwtKey ACCESS_KEY = new AccessKey(ACCESS_TOKEN_SECRET, ACCESS_TOKEN_EXPIRY);

	@Test
	@DisplayName("Claims로 토큰을 발급한다")
	void generate_token_with_claims() {
		String subject = "testsubject";

		assertThat(ACCESS_KEY.generateTokenWith(buildClaims(subject))).contains(JWT_PREFIX_HS512);
	}

	@Test
	@DisplayName("토큰에서 Claims를 가져온다")
	void parse() {
		String subject = "testsubject";
		String token = ACCESS_KEY.generateTokenWith(buildClaims(subject));

		assertThat(ACCESS_KEY.parse(token).getSubject()).isEqualTo(subject);
	}

	@Test
	@DisplayName("토큰이 유효한지 판별한다")
	void validate() {
		String subject = "testsubject";

		assertThat(ACCESS_KEY.generateTokenWith(buildClaims(subject)))
			.satisfies(token -> assertThat(ACCESS_KEY.validate(token)).isTrue());
	}

	private Claims buildClaims(String subject) {
		Claims claims = Jwts.claims();
		claims.setSubject(subject);
		return claims;
	}
}