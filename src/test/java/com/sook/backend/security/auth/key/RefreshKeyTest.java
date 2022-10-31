package com.sook.backend.security.auth.key;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RefreshKeyTest {

	private static final String REFRESH_TOKEN_SECRET = "testrefreshsecret";
	private static final JwtKey refreshKey = new RefreshKey(REFRESH_TOKEN_SECRET);

	@Test
	@DisplayName("Claims로 토큰을 발급한다")
	void generateTokenWith() {

	}

	@Test
	@DisplayName("토큰에서 Claims를 가져온다")
	void parse() {
	}

	@Test
	@DisplayName("토큰이 유효한지 판별한다")
	void validate() {

	}
}