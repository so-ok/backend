package com.sook.backend.user.dto;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import com.sook.backend.user.model.User;

import lombok.Builder;

public record UserDto(
        String username,
        String email,
        String image,
        List<AuthorityDto> authorities
) {

    @Builder
    public UserDto {
    }

    public static UserDto of(User entity) {
        List<AuthorityDto> authorities = entity.getAuthorities().stream()
                .map(AuthorityDto::of)
                .toList();

        return UserDto.builder()
                .username(entity.username())
                .email(entity.email())
                .image(entity.image())
                .authorities(authorities)
                .build();
    }

    public record AuthorityDto(
            String name
    ) {
        @Builder
        public AuthorityDto {
        }

        public static AuthorityDto of(GrantedAuthority authority) {
            return AuthorityDto.builder()
                    .name(authority.getAuthority())
                    .build();
        }
    }
}
