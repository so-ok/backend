package com.sook.backend.security.auth.dto;

import lombok.Builder;

public class TokenDto {
    public record AccessTokenDto(
            String accessToken
    ) {
        @Builder
        public AccessTokenDto {
        }

        @Override
        public String toString() {
            return "token";
        }
    }

    public record RefreshTokenDto(
            String refreshToken
    ) {
        @Builder
        public RefreshTokenDto {
        }

        @Override
        public String toString() {
            return "token";
        }
    }
}