package com.sook.backend.security.auth;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import com.sook.backend.security.auth.dto.AuthDto;
import com.sook.backend.security.auth.dto.TokenDto;
import com.sook.backend.security.auth.key.TokenProvider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtService {

    private static final String AUTHORITY_SEPARATOR = ",";
    private static final String AUTHORITY_KEY = "authorities";

    private final TokenProvider accessTokenProvider;
    private final TokenProvider refreshTokenProvider;

    public String generateAccessToken(OAuth2User oAuth2User) {
        return accessTokenProvider.issueWith(buildClaimsFrom(oAuth2User));
    }

    public String generateRefreshToken(OAuth2User oAuth2User) {
        return refreshTokenProvider.issueWith(buildClaimsFrom(oAuth2User));
    }

    public Authentication getAuthentication(String accessToken) {
        Claims claims = accessTokenProvider.parse(accessToken);
        String email = claims.getSubject();
        String joinedAuthorities = claims.get(AUTHORITY_KEY, String.class);
        return new UsernamePasswordAuthenticationToken(new AuthDto(email), "", parseAuthorities(joinedAuthorities));
    }

    public TokenDto.AccessTokenDto renewWith(String refreshToken) {
        Claims claims = refreshTokenProvider.parse(refreshToken);
        String accessToken = accessTokenProvider.issueWith(claims);
        return new TokenDto.AccessTokenDto(accessToken);
    }

    private Claims buildClaimsFrom(OAuth2User oAuth2User) {
        Claims claims = Jwts.claims();
        claims.setSubject(oAuth2User.getName());
        claims.put(AUTHORITY_KEY, joinAuthoritiesOf(oAuth2User.getAuthorities()));
        return claims;
    }

    private String joinAuthoritiesOf(Collection<? extends GrantedAuthority> authorities) {
        return authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(AUTHORITY_SEPARATOR));
    }

    private Collection<? extends GrantedAuthority> parseAuthorities(String authorities) {
        return Arrays.stream(authorities.split(AUTHORITY_SEPARATOR))
                .map(SimpleGrantedAuthority::new)
                .toList();
    }
}
