package com.example.springchatapp.app.user.controller;

import com.example.springchatapp.app.user.dto.UserCreateReqDto;
import com.example.springchatapp.app.user.dto.UserDto;
import com.example.springchatapp.app.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserCreateReqDto param) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.createUser(param));
    }
}
