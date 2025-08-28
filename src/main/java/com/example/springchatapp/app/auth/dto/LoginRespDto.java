package com.example.springchatapp.app.auth.dto;

import com.example.springchatapp.app.user.dto.UserDto;

public record LoginRespDto(boolean success, UserDto user) {
}