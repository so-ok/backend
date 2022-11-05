package com.sook.backend.security.auth;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import com.sook.backend.security.auth.dto.AuthDto;
import com.sook.backend.security.auth.dto.TokenDto;
import com.sook.backend.security.auth.key.JwtKey;
import com.sook.backend.user.model.User;
import com.sook.backend.user.service.UserService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtService {

	private final UserService userService;
	private final JwtKey accessKey;
	private final JwtKey refreshKey;

	public String generateAccessToken(OAuth2User oAuth2User) {
		Claims claims = buildClaims(oAuth2User);
		return accessKey.generateTokenWith(claims);
	}

	public String generateRefreshToken(OAuth2User oAuth2User) {
		Claims claims = buildClaims(oAuth2User);
		return refreshKey.generateTokenWith(claims);
	}

	public Authentication getAuthentication(String accessToken) {
		Claims claims = accessKey.parse(accessToken);
		String email = claims.getSubject();
		User user = userService.findUser(email);

		AuthDto authDTO = AuthDto.builder()
			.id(user.id())
			.email(user.email())
			.build();
		return new UsernamePasswordAuthenticationToken(authDTO, "", user.getAuthorities());
	}

	public boolean validateToken(String accessToken) {
		return accessKey.validate(accessToken);
	}

	public TokenDto.TokenResponseDto renew(String refreshToken) {
		Claims claims = refreshKey.parse(refreshToken);

		String accessToken = accessKey.generateTokenWith(claims);
		return new TokenDto.TokenResponseDto(accessToken);
	}

	private Claims buildClaims(OAuth2User oAuth2User) {
		String email = oAuth2User.getName();
		Claims claims = Jwts.claims();
		claims.setSubject(email);
		return claims;
	}
}
