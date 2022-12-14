package com.sook.backend.security.auth.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sook.backend.common.annotations.NoApiAuth;
import com.sook.backend.security.auth.JwtService;
import com.sook.backend.security.auth.dto.TokenDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("auth")
@Api(tags = "π μΈμ¦")
public class AuthController {

    private final JwtService jwtService;

    @NoApiAuth
    @ApiOperation(value = "ν ν° κ°±μ ", notes = "Refresh Token νμ")
    @PostMapping(path = "renew")
    public TokenDto.AccessTokenDto renewToken(@RequestBody TokenDto.RefreshTokenDto refreshTokenDto) {
        return jwtService.renewWith(refreshTokenDto.refreshToken());
    }
}
