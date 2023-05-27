package com.example.shopping_application.controller;

import com.example.shopping_application.entity.User;
import com.example.shopping_application.entity.UserType;
import com.example.shopping_application.security.CurrentUser;
import com.example.shopping_application.service.NotificationService;
import com.example.shopping_application.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;
    private final NotificationService notificationService;

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

    @GetMapping("{id}")
    public String currentUserPage(ModelMap modelmap, @PathVariable("id") int id) {
        modelmap.addAttribute("singleUser", userService.findAllById(id));
        return "singleUserPage";
    }

    @GetMapping("/notifications/{userId}")
    public String notificationPage(ModelMap modelmap, @PathVariable("userId") int id) {
        modelmap.addAttribute("notifications", notificationService.findAllByUserId(id));
        return "notifications";
    }

    @PostMapping("{id}")
    public String updateCurrentUser(@AuthenticationPrincipal CurrentUser currentUser, @RequestParam("profile_pic") MultipartFile multipartFile) throws IOException {
        userService.updatePicName(multipartFile, currentUser.getUser().getId());
        return "redirect:/user/" + currentUser.getUser().getId();
    }

    @GetMapping("/admin")
    public String adminPage(ModelMap modelMap, @AuthenticationPrincipal CurrentUser currentUser) {
        modelMap.addAttribute("currentUser", currentUser.getUser());
        return "admin";
    }
}