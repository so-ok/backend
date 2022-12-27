package com.sook.backend.security.auth.key;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Qualifier("refreshTokenProvider")
public class RefreshTokenProvider extends TokenProvider {

    public RefreshTokenProvider(@Value("${app.auth.refreshTokenSecret}") String secret,
            @Value("${app.auth.refreshTokenExpiry}") Long duration) {
        super(secret, duration);
    }
}
