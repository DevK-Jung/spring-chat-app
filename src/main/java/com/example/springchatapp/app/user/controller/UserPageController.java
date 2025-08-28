package com.example.springchatapp.app.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserPageController {
    
    @GetMapping("/register")
    public String registerPage() {
        return "auth/register";
    }
}