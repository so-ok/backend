package com.sook.backend.security.oauth.attribute;

import java.util.Collections;
import java.util.Map;

import com.sook.backend.user.model.Role;
import com.sook.backend.user.model.User;

public class KakaoOAuth2UserAttribute extends OAuth2UserAttribute {

	private static final String KAKAO_PROVIDER_ID = "kakao";
	private static final String KAKAO_ACCOUNT_KEY = "kakao_account";
	private static final String KAKAO_PROFILE_KEY = "profile";

	private final Map<String, Object> kakaoAccount;
	private final Map<String, Object> profile;
	private final String userNameAttributeName;

	public KakaoOAuth2UserAttribute(Map<String, Object> attributes, String userNameAttributeName) {
		super(attributes);
		this.kakaoAccount = (Map<String, Object>)attributes.get(KAKAO_ACCOUNT_KEY);
		this.profile = (Map<String, Object>)kakaoAccount.get(KAKAO_PROFILE_KEY);
		this.userNameAttributeName = userNameAttributeName;
	}

	@Override
	public User toEntity() {
		return User.builder()
			.roles(Collections.singleton(Role.USER))
			.oauthProvider(KAKAO_PROVIDER_ID)
			.image(profileImage())
			.userId(username())
			.username(nickname())
			.email(email())
			.build();
	}

	@Override
	public String email() {
		return kakaoAccount.get("email").toString();
	}

	@Override
	public String nickname() {
		return profile.get("nickname").toString();
	}

	@Override
	public String profileImage() {
		return profile.get("profile_image_url").toString();
	}

	@Override
	public String username() {
		return getAttributes().get(userNameAttributeName).toString();
	}
}
