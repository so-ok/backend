package com.sook.backend.security.auth;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sook.backend.security.auth.exception.InvalidTokenException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String HEADER_PREFIX = "Bearer ";
    private static final String EMPTY = "";
    private static final String INVALID_TOKEN_MESSAGE = "invalid token provided";

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        try {
            Authentication authentication = jwtService.getAuthentication(parseTokenFrom(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (InvalidTokenException exception) {
            log.error(INVALID_TOKEN_MESSAGE);
        }
        filterChain.doFilter(request, response);
    }

    private String parseTokenFrom(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith(HEADER_PREFIX)) {
            return authorizationHeader.replace(HEADER_PREFIX, EMPTY);
        }
        return null;
    }
}