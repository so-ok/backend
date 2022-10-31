package com.sook.backend.security.auth.key;

import io.jsonwebtoken.Claims;

public interface JwtKey {

	String generateTokenWith(Claims claims);

	Claims parse(String token);

	boolean validate(String token);
}
