package com.sook.backend.security.oauth;

import java.util.Optional;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sook.backend.security.oauth.attribute.OAuth2UserAttribute;
import com.sook.backend.security.oauth.attribute.OAuth2UserAttributeFactory;
import com.sook.backend.user.model.User;
import com.sook.backend.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ThirdPartyOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;

    @Transactional
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = new DefaultOAuth2UserService().loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();
        OAuth2UserAttribute attributes = OAuth2UserAttributeFactory.from(registrationId, oAuth2User.getAttributes(),
                userNameAttributeName);

        User savedUser = saveUser(attributes);
        return new SookOAuth2User(savedUser.getAuthorities(), attributes);
    }

    private User saveUser(OAuth2UserAttribute attributes) {
        Optional<User> userOptional = userRepository.findByEmail(attributes.email());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.updateImage(attributes.profileImage());
            return user;
        }

        return userRepository.save(attributes.toEntity());
    }
}