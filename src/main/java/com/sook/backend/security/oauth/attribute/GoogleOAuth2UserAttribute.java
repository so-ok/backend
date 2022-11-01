package com.sook.backend.security.oauth.attribute;

import java.util.Collections;
import java.util.Map;

import com.sook.backend.user.model.Role;
import com.sook.backend.user.model.User;

public class GoogleOAuth2UserAttribute extends OAuth2UserAttribute {

	private static final String GOOGLE_PROVIDER_ID = "google";

	private final String userNameAttributeName;

	public GoogleOAuth2UserAttribute(Map<String, Object> attributes, String userNameAttributeName) {
		super(attributes);
		this.userNameAttributeName = userNameAttributeName;
	}

	@Override
	public User toEntity() {
		return User.builder()
			.roles(Collections.singleton(Role.USER))
			.oauthProvider(GOOGLE_PROVIDER_ID)
			.image(profileImage())
			.userId(username())
			.username(nickname())
			.email(email())
			.build();
	}

	@Override
	public String email() {
		return getAttributes().get("email").toString();
	}

	@Override
	public String nickname() {
		return getAttributes().get("name").toString();
	}

	@Override
	public String profileImage() {
		return getAttributes().get("picture").toString();
	}

	@Override
	public String username() {
		return getAttributes().get(userNameAttributeName).toString();
	}
}
