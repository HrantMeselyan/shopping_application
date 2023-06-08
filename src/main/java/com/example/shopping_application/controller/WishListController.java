package com.example.shopping_application.controller;

import com.example.shopping_application.entity.Product;
import com.example.shopping_application.entity.User;
import com.example.shopping_application.entity.WishList;
import com.example.shopping_application.mapper.UserMapper;
import com.example.shopping_application.security.CurrentUser;
import com.example.shopping_application.service.ProductService;
import com.example.shopping_application.service.WishListService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/wishList")
@RequiredArgsConstructor
public class WishListController {
    private final WishListService wishListService;
    private final ProductService productService;


    @GetMapping
    public String wishListPage(ModelMap modelMap,
                               @RequestParam("userid") int id) {
        Optional<WishList> byUserId = wishListService.findByUserId(id);
        byUserId.ifPresent(wishList -> modelMap.addAttribute("wishlistById", wishList));
        return "wishlist";
    }

    @GetMapping("/add")
    public String addWishList(@RequestParam("productId") int productId,
                              @AuthenticationPrincipal CurrentUser currentUser) {
        Product byId = productService.findById(productId);
        User user = UserMapper.currentUserToUser(currentUser);
        Optional<WishList> byUserId = wishListService.findByUserId(user.getId());
        if (byUserId.isEmpty()) {
            Set<Product> products = new HashSet<>();
            products.add(byId);
            WishList wishList = new WishList();
            wishList.setUser(user);
            wishList.setProduct(products);
            wishListService.save(wishList);
            return "redirect:/products";
        }
        WishList wishList = byUserId.get();
        Set<Product> productset = wishList.getProduct();
        productset.add(byId);
        wishList.setProduct(productset);
        wishListService.save(wishList);
        return "redirect:/products";
    }

    @GetMapping("/remove")
    public String removeWishList(@RequestParam("id") int id,
                                 @AuthenticationPrincipal CurrentUser currentUser) {
        User user = UserMapper.currentUserToUser(currentUser);
        Optional<WishList> byUserId = wishListService.findByUserId(user.getId());
        if (byUserId.isPresent()) {
            Product byId = productService.findById(id);
            WishList wishList = byUserId.get();
            Set<Product> product = wishList.getProduct();
            product.remove(byId);
            wishList.setProduct(product);
            wishListService.save(wishList);
            return "redirect:/wishList?userid=" + user.getId();
        }
        return "redirect:/wishList?userid=" + user.getId();
    }
}
