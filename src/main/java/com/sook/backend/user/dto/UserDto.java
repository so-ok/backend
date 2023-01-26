package com.sook.backend.user.dto;

import com.sook.backend.user.model.User;

import lombok.Builder;

public record UserDto(
        String username,
        String email,
        String image
) {

    @Builder
    public UserDto {
    }

    public static UserDto of(User entity) {

        return UserDto.builder()
                .username(entity.username())
                .email(entity.email())
                .image(entity.image())
                .build();
    }
}
