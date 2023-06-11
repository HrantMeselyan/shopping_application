package com.example.shopping_application.controller;

import com.example.shopping_application.dto.userDto.UserShortDto;
import com.example.shopping_application.entity.User;
import com.example.shopping_application.mapper.UserMapper;
import com.example.shopping_application.security.CurrentUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class CurrentUserControllerAdvice {

    @ModelAttribute("currentUser")
    public UserShortDto currentUser(@AuthenticationPrincipal CurrentUser currentUser) {
        if (currentUser != null) {
            User user = currentUser.getUser();
            return UserMapper.userToUserShortDto(user);
        }
        return null;
    }
}