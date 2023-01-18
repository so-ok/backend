package com.sook.backend.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sook.backend.security.auth.annotation.Auth;
import com.sook.backend.security.auth.annotation.AuthorizedAdmin;
import com.sook.backend.security.auth.annotation.AuthorizedUser;
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
    @GetMapping
    @AuthorizedUser
    public ResponseEntity<UserDto> getUser(@Auth String username) {
        return getUserByEmail(username);
    }

    @ApiOperation("자신의 정보 가져오기(관리자)")
    @GetMapping("admin/{email}")
    @AuthorizedAdmin
    public ResponseEntity<UserDto> getUserWithAdminRole(@PathVariable String email) {
        return getUserByEmail(email);
    }

    @ApiOperation("다른 사람 정보 가져오기")
    @GetMapping("/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {
        UserDto userDto = userService.findBy(email);
        return ResponseEntity.ok(userDto);
    }
}
