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
        modelmap.addAttribute("singleUser", userService.findById(id));
        return "singleUserPage";
    }

    @PostMapping("{id}")
    public String updateCurrentUser(@AuthenticationPrincipal CurrentUser currentUser,
                                    @RequestParam("profile_pic") MultipartFile multipartFile) throws IOException {
        userService.updatePicName(multipartFile, currentUser.getUser().getId());
        return "redirect:/user/" + currentUser.getUser().getId();
    }
    @GetMapping("/notifications/{userId}")
    public String notificationPage(ModelMap modelmap, @PathVariable("userId") int id,
                                   @AuthenticationPrincipal CurrentUser currentUser) {
        modelmap.addAttribute("notifications", notificationService.findAllByUserId(id));
        modelmap.addAttribute("currentUser",currentUser.getUser());
        return "notifications";
    }


    @GetMapping("remove")
    public String removeUser(@RequestParam("id") int id) {
        userService.removeById(id);
        return "redirect:/user/admin/all";
    }

    @GetMapping("update")
    public String updateUserPage(@RequestParam("id") int id,ModelMap modelMap) {
        modelMap.addAttribute("user",userService.findById(id));
        return "updateUser";
    }

    @PostMapping("update/{id}")
    public String updateUser(@PathVariable("id") int id,
                             @ModelAttribute User user,
                             @RequestParam("profile_pic") MultipartFile multipartFile) throws IOException {
        User byId = userService.findById(id);
        user.setProfilePic(byId.getProfilePic());
        user.setPassword(byId.getPassword());
        userService.update(user,multipartFile);
        return "redirect:/user/admin/all";
    }

    @GetMapping("/admin")
    public String adminPage(ModelMap modelMap, @AuthenticationPrincipal CurrentUser currentUser) {
        modelMap.addAttribute("currentUser", currentUser.getUser());
        return "admin";
    }

    @GetMapping("/admin/all")
    public String allUsersPage(ModelMap modelMap, @AuthenticationPrincipal CurrentUser currentUser) {
        modelMap.addAttribute("currentUser",currentUser.getUser());
        modelMap.addAttribute("users",userService.findAll());
        return "allUsers";
    }
}