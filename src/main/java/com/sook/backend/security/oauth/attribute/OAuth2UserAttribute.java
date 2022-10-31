package com.sook.backend.security.oauth.attribute;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.sook.backend.user.model.User;

public abstract class OAuth2UserAttribute {

	private final Map<String, Object> attributes;

	public OAuth2UserAttribute(Map<String, Object> attributes) {
		this.attributes = new HashMap<>(attributes);
	}

	public Map<String, Object> getAttributes() {
		return Collections.unmodifiableMap(attributes);
	}

	public abstract String email();

	public abstract String nickname();

	public abstract String profileImage();

	public abstract String username();

	public abstract User toEntity();
}
