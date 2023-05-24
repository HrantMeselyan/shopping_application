package com.example.shopping_application.controller;

import com.example.shopping_application.entity.User;
import com.example.shopping_application.security.CurrentUser;
import com.example.shopping_application.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/register")
    public String registerPage() {
        return "/register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user) {
        userService.save(user);
        return "redirect:/";
    }

    @GetMapping("/admin")
    public String adminPage(ModelMap modelMap, @AuthenticationPrincipal CurrentUser currentUser) {
        return "admin";
    }
}