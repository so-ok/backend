package com.sook.backend.security.auth.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sook.backend.security.auth.JwtService;
import com.sook.backend.security.auth.dto.TokenDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("auth")
@Api(tags = "🔑 인증")
public class AuthController {

    private final JwtService jwtService;

    @ApiOperation(value = "토큰 갱신", notes = "Refresh Token 필요")
    @PostMapping(path = "renew")
    public TokenDto.AccessTokenDto renewToken(@RequestBody TokenDto.RefreshTokenDto refreshTokenDto) {
        return jwtService.renewWith(refreshTokenDto.refreshToken());
    }
}
