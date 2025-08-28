package com.example.springchatapp.app.user.controller;

import com.example.springchatapp.app.user.dto.UserCreateReqDto;
import com.example.springchatapp.app.user.dto.UserDto;
import com.example.springchatapp.app.user.service.UserService;
import com.example.springchatapp.common.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<UserDto> register(@Valid @RequestBody UserCreateReqDto param) {
        return ApiResponse.success(userService.createUser(param));
    }

    @GetMapping("/check-username")
    public ApiResponse<Boolean> checkUsername(@RequestParam String username) {
        boolean isTaken = userService.isUsernameTaken(username);

        if (isTaken)
            throw new IllegalArgumentException("Username is already taken");

        return ApiResponse.success(true);
    }

    @GetMapping("/check-email")
    public ApiResponse<Boolean> checkEmail(@RequestParam String email) {
        boolean isTaken = userService.isEmailTaken(email);

        if (isTaken)
            throw new IllegalArgumentException("Email is already taken");

        return ApiResponse.success(true);
    }
}
