package com.sook.backend.security.login;

import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_OK;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import net.minidev.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sook.backend.security.auth.JwtService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws
            AuthenticationException {

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            LoginDto loginDto = objectMapper.readValue(request.getInputStream(), LoginDto.class);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    loginDto.username(), loginDto.password());

            return authenticationManager.authenticate(authenticationToken);
        } catch (IOException e) {
            log.error("로그인 실패");
        }
        return null;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
        PrincipalDetails principalDetails = (PrincipalDetails)authResult.getPrincipal();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success", true);
        jsonObject.put("accessToken", jwtService.generateAccessToken(principalDetails));
        jsonObject.put("refreshToken", jwtService.generateRefreshToken(principalDetails));

        setResponseWith(response, SC_OK, jsonObject);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success", false);

        setResponseWith(response, SC_BAD_REQUEST, jsonObject);
    }

    private void setResponseWith(HttpServletResponse response, int status, JSONObject jsonObject) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");

        response.getWriter().print(jsonObject);
    }
}
