package com.example.springchatapp.app.auth.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthPageController {

    @GetMapping("/login")
    public String loginPage(Authentication authentication) {
        // 이미 로그인된 경우 채팅 페이지로 리다이렉트
        if (authentication != null && authentication.isAuthenticated()
                && !authentication.getName().equals("anonymousUser")) {
            return "redirect:/chat";
        }
        return "auth/login";
    }
}
