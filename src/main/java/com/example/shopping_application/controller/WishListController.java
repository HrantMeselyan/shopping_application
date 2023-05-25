package com.example.shopping_application.controller;

import com.example.shopping_application.entity.Order;
import com.example.shopping_application.entity.WishList;
import com.example.shopping_application.service.OrderService;
import com.example.shopping_application.service.WishListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/wishList")
@RequiredArgsConstructor
public class WishListController {
    private final WishListService wishListService;


    @GetMapping
    public String wishListPage(ModelMap modelMap){
        modelMap.addAttribute(wishListService.findAll());
        return "wishlist";
    }
    @PostMapping("/add")
    public String addOrder(@ModelAttribute WishList wishList) {
        wishListService.save(wishList);
        return "redirect:/wishList/" + wishList.getProduct().getId();
    }

    @GetMapping("/remove")
    public String removeComment(@RequestParam("id") int id, @RequestParam("productId") int productId) {
        wishListService.remove(id);
        return "redirect:/wishList/" + productId;
    }
}
