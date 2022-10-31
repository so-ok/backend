package com.sook.backend.security.auth.dto;

import static lombok.AccessLevel.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = PRIVATE)
@Builder
public class AuthDto {

    private final Long id;
    private final String email;
}