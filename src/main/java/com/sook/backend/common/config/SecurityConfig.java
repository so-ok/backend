package com.sook.backend.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;

import com.sook.backend.security.auth.JwtAuthenticationEntryPoint;
import com.sook.backend.security.auth.JwtAuthenticationFilter;
import com.sook.backend.security.auth.JwtService;
import com.sook.backend.security.oauth.CookieOAuth2AuthorizationRequestRepository;
import com.sook.backend.security.oauth.ThirdPartyOAuth2UserService;
import com.sook.backend.security.oauth.handler.OAuth2FailureHandler;
import com.sook.backend.security.oauth.handler.OAuth2SuccessHandler;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	private final AccessDeniedHandler accessDeniedHandler;
	private final JwtService jwtService;
	private final ThirdPartyOAuth2UserService thirdPartyOAuth2UserService;
	private final OAuth2SuccessHandler successHandler;
	private final OAuth2FailureHandler failureHandler;
	private final CookieOAuth2AuthorizationRequestRepository oAuth2AuthorizationRequestRepository;

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
				.antMatchers("/api/pill/**").permitAll()
				.requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
				.anyRequest().authenticated()

				.and()
				.oauth2Login()
				.authorizationEndpoint()
				.authorizationRequestRepository(oAuth2AuthorizationRequestRepository)
				.and()
				.userInfoEndpoint()
				.userService(thirdPartyOAuth2UserService)
				.and()
				.successHandler(successHandler)
				.failureHandler(failureHandler)
				.and()

				.exceptionHandling()
				.authenticationEntryPoint(jwtAuthenticationEntryPoint)
				.accessDeniedHandler(accessDeniedHandler);

		http.addFilterBefore(new JwtAuthenticationFilter(jwtService), UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
}
