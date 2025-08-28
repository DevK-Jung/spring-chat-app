package com.example.springchatapp.app.user.service;

import com.example.springchatapp.app.user.dto.UserCreateReqDto;
import com.example.springchatapp.app.user.dto.UserDto;
import com.example.springchatapp.app.user.entity.User;
import com.example.springchatapp.app.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserDto createUser(UserCreateReqDto param) {
        validateUser(param);

        User createUser = User.builder()
                .username(param.getUsername())
                .email(param.getEmail())
                .password(passwordEncoder.encode(param.getPassword()))
                .nickname(param.getNickname())
                .status(User.UserStatus.OFFLINE)
                .build();

        userRepository.save(createUser);

        return UserDto.fromEntity(createUser);
    }

    private void validateUser(UserCreateReqDto param) {
        if (userRepository.existsByUsername(param.getUsername()))
            throw new IllegalArgumentException("Username already exists");

        if (userRepository.existsByEmail(param.getEmail()))
            throw new IllegalArgumentException("Email already exists");
    }

    public void updateUserStatus(String username, User.UserStatus status) {
        userRepository.findByUsername(username)
                .ifPresent(user -> {
                    user.setStatus(status);
                    userRepository.save(user);
                });
    }

    public List<User> getOnlineUsers() {
        return userRepository.findByStatus(User.UserStatus.ONLINE);
    }
}
