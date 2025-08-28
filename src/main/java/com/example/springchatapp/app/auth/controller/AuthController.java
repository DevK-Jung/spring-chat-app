package com.example.springchatapp.app.auth.controller;

import com.example.springchatapp.app.auth.dto.LoginReqDto;
import com.example.springchatapp.app.auth.dto.LoginRespDto;
import com.example.springchatapp.app.auth.service.AuthService;
import com.example.springchatapp.app.user.dto.UserDto;
import com.example.springchatapp.common.dto.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ApiResponse<LoginRespDto> login(@Valid @RequestBody LoginReqDto loginRequest,
                                           HttpServletRequest request) {
        return ApiResponse.success(authService.login(loginRequest, request));
    }

    @PostMapping("/logout")
    public ApiResponse<Void> logout(HttpServletRequest request, Authentication authentication) {
        authService.logout(request, authentication);

        return ApiResponse.success();

    }

    @GetMapping("/me")
    public ApiResponse<UserDto> getCurrentUser(Authentication authentication) {

        return ApiResponse.success(authService.getCurrentUser(authentication));
    }
}