package com.example.shopping_application.controller;

import com.example.shopping_application.dto.cartDto.CartDto;
import com.example.shopping_application.dto.cartDto.CartItemDto;
import com.example.shopping_application.dto.userDto.UserShortDto;
import com.example.shopping_application.entity.User;
import com.example.shopping_application.mapper.UserMapper;
import com.example.shopping_application.security.CurrentUser;
import com.example.shopping_application.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@ControllerAdvice
@RequiredArgsConstructor
public class CurrentUserControllerAdvice {
    private final CartService cartService;

    @ModelAttribute("currentUser")
    public UserShortDto currentUser(@AuthenticationPrincipal CurrentUser currentUser) {
        if (currentUser != null) {
            User user = currentUser.getUser();
            return UserMapper.userToUserShortDto(user);
        }
        return null;
    }

    @ModelAttribute("cartItems")
    public List<CartItemDto> currentUserCart(@AuthenticationPrincipal CurrentUser currentUser) {
        if (currentUser != null) {
            return cartService.findLastCartItemsByLimit(currentUser.getUser().getId());
        }
        return null;
    }
}