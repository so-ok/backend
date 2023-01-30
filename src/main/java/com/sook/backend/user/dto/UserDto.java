package com.sook.backend.user.dto;

import com.sook.backend.user.model.User;

import lombok.Builder;

public class UserDto {

    public record Response(
            String username,
            String email,
            String image
    ) {

        @Builder
        public Response {
        }

        public static Response of(User entity) {
            return Response.builder()
                    .username(entity.username())
                    .email(entity.email())
                    .image(entity.image())
                    .build();
        }
    }

    public record RegisterRequest(
            String username,
            String password,
            String email,
            String image
    ) {

        @Builder
        public RegisterRequest {
        }

        public static Response of(User entity) {
            return Response.builder()
                    .username(entity.username())
                    .email(entity.email())
                    .image(entity.image())
                    .build();
        }
    }

}
