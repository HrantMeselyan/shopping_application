package com.example.shopping_application.controller;

import com.example.shopping_application.dto.wishlistDto.WishlistDto;
import com.example.shopping_application.dto.wishlistDto.WishlistResponseDto;
import com.example.shopping_application.entity.WishList;
import com.example.shopping_application.mapper.UserMapper;
import com.example.shopping_application.security.CurrentUser;
import com.example.shopping_application.service.WishListService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("/wishList")
@RequiredArgsConstructor
public class WishListController {
    private final WishListService wishListService;


    @GetMapping
    public String wishListPage(ModelMap modelMap,
                               @RequestParam("userid") int id) {
        modelMap.addAttribute("wishlistById", wishListService.findByUserId(id));
        return "wishlist";
    }

    @GetMapping("/add")
    public String addWishList(@RequestParam("productId") int productId,
                              @AuthenticationPrincipal CurrentUser currentUser) {
        wishListService.save(productId, currentUser);
        return "redirect:/products";
    }

    @GetMapping("/remove")
    public String removeWishList(@RequestParam("id") int id,
                                 @AuthenticationPrincipal CurrentUser currentUser) {
        wishListService.remove(id, currentUser);
        return "redirect:/wishList?userid=" + UserMapper.currentUserToUser(currentUser).getId();
    }
}
