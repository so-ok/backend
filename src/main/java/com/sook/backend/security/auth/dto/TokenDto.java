package com.sook.backend.security.auth.dto;

import lombok.Builder;

public record TokenDto(
        String token
) {
    @Builder
    public TokenDto {
    }

    @Override
    public String toString() {
        return "token";
    }
}