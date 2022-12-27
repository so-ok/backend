package com.sook.backend.security.auth.key;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Qualifier("refreshSecret")
public class RefreshSecret extends Secret {

    public RefreshSecret(@Value("${app.auth.refreshTokenSecret}") String secret,
            @Value("${app.auth.refreshTokenExpiry}") Long duration) {
        super(secret, duration);
    }
}
