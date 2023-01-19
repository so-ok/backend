package com.sook.backend.security.auth;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sook.backend.common.exception.UserNotFoundException;
import com.sook.backend.security.auth.dto.TokenDto;
import com.sook.backend.security.auth.key.TokenProvider;
import com.sook.backend.security.login.PrincipalDetails;
import com.sook.backend.user.model.User;
import com.sook.backend.user.repository.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final UserRepository userRepository;
    private final TokenProvider accessTokenProvider;
    private final TokenProvider refreshTokenProvider;

    public String generateAccessToken(UserDetails userDetails) {
        return accessTokenProvider.issueWith(buildClaimsFrom(userDetails));
    }

    public String generateRefreshToken(UserDetails userDetails) {
        return refreshTokenProvider.issueWith(buildClaimsFrom(userDetails));
    }

    public Authentication getAuthentication(String accessToken) {
        Claims claims = accessTokenProvider.parse(accessToken);
        String email = claims.getSubject();
        UserDetails userDetails = getUserBy(email);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public TokenDto.AccessTokenDto renewWith(String refreshToken) {
        Claims claims = refreshTokenProvider.parse(refreshToken);
        String accessToken = accessTokenProvider.issueWith(claims);
        return new TokenDto.AccessTokenDto(accessToken);
    }

    private Claims buildClaimsFrom(UserDetails userDetails) {
        Claims claims = Jwts.claims();
        claims.setSubject(userDetails.getUsername());
        return claims;
    }

    @Transactional
    UserDetails getUserBy(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);
        return new PrincipalDetails(user);
    }
}
