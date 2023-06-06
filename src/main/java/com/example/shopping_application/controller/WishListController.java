package com.example.shopping_application.controller;

import com.example.shopping_application.entity.Product;
import com.example.shopping_application.entity.User;
import com.example.shopping_application.entity.WishList;
import com.example.shopping_application.security.CurrentUser;
import com.example.shopping_application.service.ProductService;
import com.example.shopping_application.service.WishListService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/wishList")
@RequiredArgsConstructor
public class WishListController {
    private final WishListService wishListService;
    private final ProductService productService;


    @GetMapping
    public String wishListPage(ModelMap modelMap,
                               @RequestParam("userid") int id) {
        modelMap.addAttribute("wishlistById", wishListService.findAllByUserId(id));
        return "wishlist";
    }

    @GetMapping("/add")
    public String addWishList(@ModelAttribute WishList wishList,
                              @RequestParam("productId") int productId,
                              @AuthenticationPrincipal CurrentUser currentUser) {
        Product byId = productService.findById(productId);
        Optional<WishList> wishList1 = wishListService.findByProduct(byId);
        if (currentUser != null) {
            if (wishList1.isEmpty()) {
                User user = currentUser.getUser();
                wishList.setUser(user);
                wishList.setProduct(byId);
                wishListService.save(wishList);
                return "redirect:/products";
            }
            return "redirect:/products";
        }
        return "redirect:/user/register";
    }

    @GetMapping("/remove")
    public String removeWishList(@RequestParam("id") int id,
                                 @AuthenticationPrincipal CurrentUser currentUser) {
        wishListService.removeByProductId(id);
        return "redirect:/wishList?userid=" + currentUser.getUser().getId();
    }
}
