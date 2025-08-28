package com.example.springchatapp.app.auth.service;

import com.example.springchatapp.app.auth.dto.LoginReqDto;
import com.example.springchatapp.app.auth.dto.LoginRespDto;
import com.example.springchatapp.app.user.dto.UserDto;
import com.example.springchatapp.app.user.entity.User;
import com.example.springchatapp.app.user.service.UserService;
import com.example.springchatapp.common.dto.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;

    private final UserService userService;

    public LoginRespDto login(LoginReqDto loginRequest, HttpServletRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            // 인증 성공 시 SecurityContext에 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 세션 생성
            HttpSession session = request.getSession(true);
            session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                    SecurityContextHolder.getContext());

            // 사용자 정보 조회 및 상태 업데이트
            User user = userService.findByUsername(loginRequest.getUsername())
                    .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

            // 사용자 상태를 ONLINE으로 변경
            userService.updateUserStatus(user.getUsername(), User.UserStatus.ONLINE);

            return new LoginRespDto(true, UserDto.fromEntity(user));
        } catch (BadCredentialsException e) {
            throw new IllegalArgumentException("아이디 또는 비밀번호가 올바르지 않습니다.");
        } catch (Exception e) {
            throw new RuntimeException("로그인 중 오류가 발생했습니다.");
        }
    }

    public void logout(HttpServletRequest request, Authentication authentication) {
        try {
            // 현재 사용자 상태를 OFFLINE으로 변경
            if (authentication != null && authentication.isAuthenticated()) {
                String username = authentication.getName();
                userService.updateUserStatus(username, User.UserStatus.OFFLINE);
            }

            // 세션 무효화
            HttpSession session = request.getSession(false);
            if (session != null) session.invalidate();

            // SecurityContext 클리어
            SecurityContextHolder.clearContext();

        } catch (Exception e) {
            throw new RuntimeException("로그아웃 중 오류가 발생했습니다.");
        }
    }

    public UserDto getCurrentUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalArgumentException("인증되지 않은 사용자입니다.");
        }

        User user = userService.findByUsername(authentication.getName())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        return UserDto.fromEntity(user);
    }
}
