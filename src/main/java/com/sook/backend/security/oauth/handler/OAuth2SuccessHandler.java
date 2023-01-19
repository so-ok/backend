package com.sook.backend.security.oauth.handler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Optional;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.sook.backend.security.auth.JwtService;
import com.sook.backend.security.oauth.CookieOAuth2AuthorizationRequestRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    @Value("${frontend.redirectUri}")
    private String redirectionUri;
    @Value("${frontend.authorizedRedirectUris}")
    private String[] authorizedUris;

    private final JwtService jwtService;
    private final CookieOAuth2AuthorizationRequestRepository requestRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        String targetUrl = getTargetUrl(request, authentication);
        requestRepository.removeAuthorizationRequestCookies(request, response);
        response.sendRedirect(targetUrl);
    }

    private String getTargetUrl(HttpServletRequest request, Authentication authentication) {
        UserDetails oAuth2User = (UserDetails)authentication.getPrincipal();

        String accessToken = jwtService.generateAccessToken(oAuth2User);
        String refreshToken = jwtService.generateRefreshToken(oAuth2User);

        Optional<String> redirectUriOptional = requestRepository.getRedirectUriFromCookies(request)
                .map(Cookie::getValue);
        String targetUrl = selectUri(redirectUriOptional);
        return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("accessToken", accessToken)
                .queryParam("refreshToken", refreshToken)
                .encode(StandardCharsets.UTF_8)
                .build()
                .toUriString();
    }

    private String selectUri(Optional<String> redirectUriOptional) {
        return redirectUriOptional
                .filter(this::isAuthorizedPattern)
                .orElse(redirectionUri);
    }

    private boolean isAuthorizedPattern(String requestRedirectUri) {
        return Arrays.stream(authorizedUris)
                .anyMatch(authorizedUri -> Pattern.matches(authorizedUri, requestRedirectUri));
    }
}
