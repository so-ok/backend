package com.sook.backend.security.auth.key;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Qualifier("accessSecret")
public class AccessSecret extends Secret {

    public AccessSecret(@Value("${app.auth.accessTokenSecret}") String secret,
            @Value("${app.auth.accessTokenExpiry}") Long duration) {
        super(secret, duration);
    }
}
