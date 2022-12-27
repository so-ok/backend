package com.sook.backend.security.auth;

import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import com.sook.backend.security.auth.dto.AuthDto;
import com.sook.backend.security.auth.dto.TokenDto;
import com.sook.backend.security.auth.key.Key;
import com.sook.backend.user.dto.UserDto;
import com.sook.backend.user.service.UserService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtService {

    private final UserService userService;
    private final Key accessKey;
    private final Key refreshKey;

    public String generateAccessToken(OAuth2User oAuth2User) {
        Claims claims = buildClaimsFrom(oAuth2User);
        return accessKey.issueTokenWith(claims);
    }

    public String generateRefreshToken(OAuth2User oAuth2User) {
        Claims claims = buildClaimsFrom(oAuth2User);
        return refreshKey.issueTokenWith(claims);
    }

    public Authentication getAuthentication(String accessToken) {
        Claims claims = accessKey.parse(accessToken);
        String email = claims.getSubject();
        UserDto userDto = userService.findBy(email);

        AuthDto authDto = new AuthDto(userDto.email());
        List<SimpleGrantedAuthority> authorities = userDto.authorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.name()))
                .toList();
        return new UsernamePasswordAuthenticationToken(authDto, "", authorities);
    }

    public boolean isValid(String accessToken) {
        return accessKey.isValid(accessToken);
    }

    public TokenDto.AccessToken renewWith(String refreshToken) {
        Claims claims = refreshKey.parse(refreshToken);

        String accessToken = accessKey.issueTokenWith(claims);
        return new TokenDto.AccessToken(accessToken);
    }

    private Claims buildClaimsFrom(OAuth2User oAuth2User) {
        String email = oAuth2User.getName();
        Claims claims = Jwts.claims();
        claims.setSubject(email);
        return claims;
    }
}
