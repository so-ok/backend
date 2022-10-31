package com.sook.backend.security.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sook.backend.security.auth.JwtService;
import com.sook.backend.security.auth.dto.TokenDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("auth")
public class AuthController {

	private final JwtService jwtService;

	@GetMapping(path = "renew/{refreshToken}")
	public TokenDto.TokenResponseDto renewToken(@PathVariable String refreshToken) {
		return jwtService.renew(refreshToken);
	}
}
