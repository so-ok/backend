package com.sook.backend.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public ResponseEntity<UserDto.Response> getUser(@Auth String username) {
        return getUserByEmail(username);
    }

    @ApiOperation("자신의 정보 가져오기(관리자)")
    @GetMapping("admin/{email}")
    @AuthorizedAdmin
    public ResponseEntity<UserDto.Response> getUserWithAdminRole(@PathVariable String email) {
        return getUserByEmail(email);
    }

    @ApiOperation("다른 사람 정보 가져오기")
    @GetMapping("/{email}")
    public ResponseEntity<UserDto.Response> getUserByEmail(@PathVariable String email) {
        UserDto.Response response = userService.findBy(email);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("회원가입하기")
    @PostMapping
    public ResponseEntity<UserDto.Response> register(@RequestBody UserDto.RegisterRequest request) {
        UserDto.Response response = userService.register(request);
        return ResponseEntity.ok(response);
    }
}
