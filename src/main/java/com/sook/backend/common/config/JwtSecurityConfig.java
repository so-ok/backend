package com.sook.backend.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.sook.backend.security.auth.JwtAuthenticationEntryPoint;
import com.sook.backend.security.auth.JwtAuthenticationFilter;
import com.sook.backend.security.auth.JwtService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EnableWebSecurity
public class JwtSecurityConfig {

	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	private final AccessDeniedHandler accessDeniedHandler;
	private final JwtService jwtService;

	@Bean
	public SecurityFilterChain jwtFilterChain(HttpSecurity http) throws Exception {
		http
			.exceptionHandling()
			.authenticationEntryPoint(jwtAuthenticationEntryPoint)
			.accessDeniedHandler(accessDeniedHandler);

		http.addFilterBefore(new JwtAuthenticationFilter(jwtService), UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
}
