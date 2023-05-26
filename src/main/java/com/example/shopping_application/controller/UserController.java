package com.example.shopping_application.controller;

import com.example.shopping_application.entity.User;
import com.example.shopping_application.entity.UserType;
import com.example.shopping_application.security.CurrentUser;
import com.example.shopping_application.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String registerPage() {
        return "/register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setUserType(UserType.USER);
        userService.save(user);
        return "redirect:/";
    }

    @GetMapping("/admin")
    public String adminPage(ModelMap modelMap, @AuthenticationPrincipal CurrentUser currentUser) {
        modelMap.addAttribute("currentUser",currentUser.getUser());
        return "admin";
    }
}