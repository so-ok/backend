package com.sook.backend.security.auth.dto;

import lombok.Builder;

public class TokenDto {
    public record AccessToken(
            String accessToken
    ) {
        @Builder
        public AccessToken {
        }

        @Override
        public String toString() {
            return "token";
        }
    }

    public record RefreshToken(
            String refreshToken
    ) {
        @Builder
        public RefreshToken {
        }

        @Override
        public String toString() {
            return "token";
        }
    }
}