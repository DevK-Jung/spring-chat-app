package com.example.springchatapp.app.user.dto;

import com.example.springchatapp.app.user.entity.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDto {
    private final Long id;
    private final String username;
    private final String email;
    private final String nickname;

    public static UserDto fromEntity(User user) {
        return new UserDto(user.getId(), user.getUsername(), user.getEmail(), user.getNickname());
    }
}
