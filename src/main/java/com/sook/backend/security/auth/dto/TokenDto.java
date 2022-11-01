package com.sook.backend.security.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class TokenDto {

	@AllArgsConstructor
	@Getter
	public static class TokenRequestDto {

		private String refreshToken;
	}

	@AllArgsConstructor
	@Getter
	public static class TokenResponseDto {

		private String accessToken;
	}
}
