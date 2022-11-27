package com.sook.backend.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sook.backend.common.exception.UserNotFoundException;
import com.sook.backend.user.dto.UserDto;
import com.sook.backend.user.model.User;
import com.sook.backend.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private User findUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);
    }

    public UserDto findBy(String email) {
        return UserDto.of(findUser(email));
    }
}
