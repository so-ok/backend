package com.sook.backend.security.auth.key;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Qualifier("accessKey")
public class AccessKey extends Key {

    public AccessKey(@Value("${app.auth.accessTokenSecret}") String secret,
            @Value("${app.auth.accessTokenExpiry}") Long duration) {
        super(new Secret(secret), duration);
    }
}
