package com.sook.backend.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sook.backend.common.annotations.NoApiAuth;
import com.sook.backend.security.auth.Auth;
import com.sook.backend.security.auth.dto.AuthDto;
import com.sook.backend.user.dto.UserDto;
import com.sook.backend.user.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@Api(tags = "🙋🏻‍♀️ 사용자")
public class UserController {

    private final UserService userService;

    @ApiOperation("자신의 정보 가져오기")
    @GetMapping()
    public ResponseEntity<UserDto> getUser(@Auth AuthDto authDto) {
        return getUser(authDto.email());
    }

    @NoApiAuth
    @ApiOperation("다른 사람 정보 가져오기")
    @GetMapping("/{email}")
    public ResponseEntity<UserDto> getUser(@PathVariable String email) {
        UserDto userDto = userService.findBy(email);
        return ResponseEntity.ok(userDto);
    }
}
