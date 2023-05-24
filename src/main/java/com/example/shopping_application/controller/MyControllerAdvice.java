package com.example.shopping_application.controller;

import com.example.shopping_application.entity.User;
import com.example.shopping_application.security.CurrentUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class MyControllerAdvice {

    @ModelAttribute("currentUser")
    public User curretnUser(@AuthenticationPrincipal CurrentUser currentUser){
        if(currentUser != null){
            return currentUser.getUser();
        }
        return null;
    }
}