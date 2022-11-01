package com.sook.backend.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import com.sook.backend.security.oauth.CookieOAuth2AuthorizationRequestRepository;
import com.sook.backend.security.oauth.ThirdPartyOAuth2UserService;
import com.sook.backend.security.oauth.handler.OAuth2FailureHandler;
import com.sook.backend.security.oauth.handler.OAuth2SuccessHandler;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EnableWebSecurity
public class OAuth2SecurityConfig {

	private final ThirdPartyOAuth2UserService thirdPartyOAuth2UserService;
	private final OAuth2SuccessHandler successHandler;
	private final OAuth2FailureHandler failureHandler;
	private final CookieOAuth2AuthorizationRequestRepository oAuth2AuthorizationRequestRepository;

	@Bean
	public SecurityFilterChain oAuthFilterChain(HttpSecurity http) throws Exception {
		http
			.oauth2Login()
			.authorizationEndpoint()
			.authorizationRequestRepository(oAuth2AuthorizationRequestRepository)
			.and()
			.userInfoEndpoint()
			.userService(thirdPartyOAuth2UserService)
			.and()
			.successHandler(successHandler)
			.failureHandler(failureHandler);

		return http.build();
	}
}
