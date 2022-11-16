package com.sook.backend.security.oauth;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.sook.backend.security.oauth.attribute.OAuth2UserAttribute;

public class SookOAuth2User implements OAuth2User {

    private final Collection<? extends GrantedAuthority> authorities;
    private final Map<String, Object> attributes;

    private final String email;

    public SookOAuth2User(Collection<? extends GrantedAuthority> authorities, OAuth2UserAttribute oAuth2UserAttribute) {
        this.authorities = authorities;
        this.attributes = oAuth2UserAttribute.getAttributes();
        this.email = oAuth2UserAttribute.email();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getName() {
        return email;
    }
}
