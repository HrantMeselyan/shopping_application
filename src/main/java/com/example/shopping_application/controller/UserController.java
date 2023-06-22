package com.example.shopping_application.controller;

import com.example.shopping_application.dto.userDto.UpdatePasswordDto;
import com.example.shopping_application.dto.userDto.UserDto;
import com.example.shopping_application.dto.userDto.UserRegisterDto;
import com.example.shopping_application.dto.userDto.UserUpdateDto;
import com.example.shopping_application.entity.Order;
import com.example.shopping_application.entity.User;
import com.example.shopping_application.mapper.OrderMapper;
import com.example.shopping_application.mapper.UserMapper;
import com.example.shopping_application.security.CurrentUser;
import com.example.shopping_application.service.MailService;
import com.example.shopping_application.service.NotificationService;
import com.example.shopping_application.service.OrderService;
import com.example.shopping_application.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
@Validated
public class UserController {
    private final MailService mailService;
    private final UserService userService;
    private final OrderService orderService;
    private final NotificationService notificationService;

    @Value("${site.url}")
    private String siteUrl;

    @GetMapping("/register")
    public String registerPage(ModelMap modelMap) {
        modelMap.addAttribute("userRegisterDto", UserMapper.userToUserRegisterDto(new User()));
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute UserRegisterDto userRegisterDto, Errors errors) {
        if (errors.hasErrors()) {
            return "register";
        }
        User user = userService.save(userRegisterDto);
        if (user != null) {
            mailService.sendMail(userRegisterDto.getEmail(), "Welcome",
                    "Hi " + userRegisterDto.getName() +
                            " Welcome please verify your account by clicking " + siteUrl + "/user/verify?email=" + user.getEmail() + "&token=" + user.getToken()
            );
        }
        return "redirect:/customLogin";
    }

    @GetMapping()
    public String currentUserPage(ModelMap modelmap,
                                  @AuthenticationPrincipal CurrentUser currentUser) {
        modelmap.addAttribute("userUpdateDto", new UserUpdateDto());
        modelmap.addAttribute("updatePasswordDto", new UpdatePasswordDto());
        modelmap.addAttribute("user", userService.findByIdWithAddresses(UserMapper.currentUserToUser(currentUser).getId()));
        return "singleUserPage";
    }



    @PostMapping("/updatePassword")
    public String updatePassword(@Valid @ModelAttribute UpdatePasswordDto updatePasswordDto,
                                 @AuthenticationPrincipal CurrentUser currentUser) {
        userService.updatePassword(UserMapper.currentUserToUser(currentUser), updatePasswordDto);
        return "redirect:/user";
    }


    @PostMapping("/updateUserData")
    public String updateCurrentUser(@Valid @ModelAttribute UserUpdateDto userUpdateDto,
                                    @AuthenticationPrincipal CurrentUser currentUser,
                                    @RequestParam("profile_pic") MultipartFile multipartFile) throws IOException {
        userService.updateUser(multipartFile, UserMapper.userUpdateDtoToUser(userUpdateDto), currentUser);
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
        modelMap.addAttribute("user", UserMapper.userToUserDto(userService.findById(id)));
        return "updateUser";
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


    @GetMapping("/order")
    public String userOrderPage(@AuthenticationPrincipal CurrentUser currentUser, ModelMap modelMap) {
        List<Order> allByUserId = orderService.findAllByUserId(currentUser.getUser().getId());
        modelMap.addAttribute("user", UserMapper.currentUserToUser(currentUser));
        modelMap.addAttribute(OrderMapper.listOrderToListOrderDto(allByUserId));
        return "account-orders";
    }

    @GetMapping("/payment")
    public String userPaymentPage(@AuthenticationPrincipal CurrentUser currentUser, ModelMap modelMap) {
        modelMap.addAttribute("user", UserMapper.currentUserToUser(currentUser));
        return "account-payment";
    }

    @GetMapping("/verify")
    public String verifyEmail(@RequestParam("email") String email, @RequestParam("token") UUID token) {
        boolean isVerified = userService.verifyUserByEmail(email, token);
        if (isVerified) {
            return "redirect:/customLogin";
        }
        return "redirect:/";
    }

}