package com.sook.backend.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable()

			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()

			.formLogin().disable()
			.httpBasic().disable()

			.authorizeRequests()
			.antMatchers("/docs", "/swagger-ui/**", "/swagger-resources/**", "/v3/api-docs").permitAll()
			.antMatchers("/auth/renew").permitAll()
			.requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
			.anyRequest().authenticated();
		return http.build();
	}
}