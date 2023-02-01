package com.sook.backend.user.service;

import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sook.backend.user.dto.UserDto;
import com.sook.backend.user.exception.DuplicateEmailException;
import com.sook.backend.user.exception.UserNotFoundException;
import com.sook.backend.user.model.Role;
import com.sook.backend.user.model.User;
import com.sook.backend.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private User findUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);
    }

    public UserDto.Response findBy(String email) {
        return UserDto.Response.of(findUser(email));
    }

    @Transactional
    public UserDto.Response register(UserDto.RegisterRequest registerRequest) {
        User user = User.builder()
                .username(registerRequest.username())
                .password(passwordEncoder.encode(registerRequest.password()))
                .email(registerRequest.email())
                .roles(Set.of(Role.USER))
                .image(registerRequest.image())
                .build();
        validateUnique(user);
        userRepository.save(user);
        return UserDto.Response.of(user);
    }

    private void validateUnique(User user) {
        if (userRepository.findByEmail(user.email()).isPresent()) {
            throw new DuplicateEmailException();
        }
    }
}
