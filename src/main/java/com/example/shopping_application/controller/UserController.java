package com.example.shopping_application.controller;

import com.example.shopping_application.dto.userDto.UserDto;
import com.example.shopping_application.dto.userDto.UserRegisterDto;
import com.example.shopping_application.entity.Role;
import com.example.shopping_application.entity.User;
import com.example.shopping_application.mapper.UserMapper;
import com.example.shopping_application.security.CurrentUser;
import com.example.shopping_application.service.NotificationService;
import com.example.shopping_application.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;
    private final NotificationService notificationService;

    @GetMapping("/register")
    public String registerPage(ModelMap modelMap) {
        modelMap.addAttribute("userRegisterDto", UserMapper.userToUserRegisterDto(new User()));
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute UserRegisterDto userRegisterDto,
                           Errors errors) {
        if (errors.hasErrors()) {
            return "register";
        }
        User user = UserMapper.userRegisterDtoToUser(userRegisterDto);
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setRole(Role.USER);
        userService.save(user);
        return "redirect:/";
    }

    @GetMapping()
    public String currentUserPage(ModelMap modelmap,
                                  @AuthenticationPrincipal CurrentUser currentUser) {
        modelmap.addAttribute("user", userService.findByIdWithAddresses(UserMapper.currentUserToUser(currentUser).getId()));
        return "singleUserPage";
    }

    @PostMapping()
    public String updateCurrentUser(@AuthenticationPrincipal CurrentUser currentUser,
                                    @RequestParam("profile_pic") MultipartFile multipartFile) throws IOException {
        userService.updatePicName(multipartFile, UserMapper.currentUserToUser(currentUser));
        return "redirect:/user";
    }

    @GetMapping("/notifications/{userId}")
    public String notificationPage(ModelMap modelmap,
                                   @PathVariable("userId") int id,
                                   @AuthenticationPrincipal CurrentUser currentUser) {
        modelmap.addAttribute("notifications", notificationService.findAllByUserId(id));
        modelmap.addAttribute("currentUser", UserMapper.currentUserToUser(currentUser));
        return "notifications";
    }


    @GetMapping("remove")
    public String removeUser(@RequestParam("id") int id) {
        userService.removeById(id);
        return "redirect:/user/admin/all";
    }

    @GetMapping("update")
    public String updateUserPage(@RequestParam("id") int id,
                                 ModelMap modelMap) {
        modelMap.addAttribute("user", userService.findById(id));
        return "updateUser";
    }

    @PostMapping("update/{id}")
    public String updateUser(@PathVariable("id") int id,
                             @ModelAttribute UserDto userDto,
                             @RequestParam("profile_pic") MultipartFile multipartFile) throws IOException {
        User user = UserMapper.userDtoToUser(userDto);
        User byId = userService.findById(id);
        user.setProfilePic(byId.getProfilePic());
        user.setPassword(byId.getPassword());
        userService.update(user, multipartFile);
        return "redirect:/user/admin/all";
    }

    @GetMapping("/admin")
    public String adminPage(ModelMap modelMap,
                            @AuthenticationPrincipal CurrentUser currentUser) {
        modelMap.addAttribute("currentUser", UserMapper.currentUserToUser(currentUser));
        return "admin";
    }

    @GetMapping("/forgotPassword")
    public String forgotPasswordPage() {
        return "reset-password";
    }

    @GetMapping("/admin/all")
    public String allUsersPage(ModelMap modelMap,
                               @AuthenticationPrincipal CurrentUser currentUser) {
        modelMap.addAttribute("currentUser", UserMapper.currentUserToUser(currentUser));
        List<User> all = userService.findAll();
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : all) {
            userDtos.add(UserMapper.userToUserDto(user));
        }
        modelMap.addAttribute("users", userDtos);
        return "allUsers";
    }
}