package com.example.shopping_application.controller;

import com.example.shopping_application.dto.cartDto.CartItemDto;
import com.example.shopping_application.dto.userDto.UpdatePasswordDto;
import com.example.shopping_application.dto.userDto.UserShortDto;
import com.example.shopping_application.dto.userDto.UserUpdateDto;
import com.example.shopping_application.entity.User;
import com.example.shopping_application.mapper.UserMapper;
import com.example.shopping_application.security.CurrentUser;
import com.example.shopping_application.service.CartService;
import com.example.shopping_application.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@ControllerAdvice
@RequiredArgsConstructor
public class CurrentUserControllerAdvice {
    private final CartService cartService;

    private final UserService userService;

    @ModelAttribute("currentUser")
    public UserShortDto currentUser(@AuthenticationPrincipal CurrentUser currentUser) {
        if (currentUser != null) {
            User user = currentUser.getUser();
            return UserMapper.userToUserShortDto(user);
        }
        return null;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ModelAndView handleIllegalArgument(MethodArgumentNotValidException methodArgumentNotValidException,
                                              @AuthenticationPrincipal CurrentUser currentUser,
                                              BindingResult errors){
        ModelAndView modelAndView = new ModelAndView();
        List<FieldError> fieldErrors = methodArgumentNotValidException.getBindingResult().getFieldErrors();

        for (FieldError fieldError : fieldErrors) {
            modelAndView.addObject(fieldError.getField()+"_",fieldError.getDefaultMessage());
        }
        Object target = errors.getTarget();
        if (target instanceof UserUpdateDto) {
            modelAndView.addObject("updatePasswordDto",new UpdatePasswordDto());
        }else {
            modelAndView.addObject("userUpdateDto",new UserUpdateDto());
        }
        modelAndView.addObject(toLowerCase(errors.getTarget().getClass().getSimpleName()), errors.getTarget());
        modelAndView.addObject("user", userService.findByIdWithAddresses(UserMapper.currentUserToUser(currentUser).getId()));
        modelAndView.setViewName("singleUserPage");
        return modelAndView;
    }


    private String toLowerCase(String str) {
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }

    @ModelAttribute("cartItems")
    public List<CartItemDto> currentUserCart(@AuthenticationPrincipal CurrentUser currentUser) {
        if (currentUser != null) {
            return cartService.findLastCartItemsByLimit(currentUser.getUser().getId());
        }
        return null;
    }
}