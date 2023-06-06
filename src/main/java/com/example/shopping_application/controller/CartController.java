package com.example.shopping_application.controller;

import com.example.shopping_application.security.CurrentUser;
import com.example.shopping_application.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping()
    public String cartPage(ModelMap modelMap, @AuthenticationPrincipal CurrentUser currentUser) {
        modelMap.addAttribute("carts", cartService.findAllByUser_id(currentUser.getUser().getId()));
        return "cart";
    }

    @GetMapping("/add/{productId}")
    public String saveCart(@PathVariable("productId") int id, @AuthenticationPrincipal CurrentUser currentUser) {
        cartService.save(id, currentUser);
        return "redirect:/products";
    }

    @PostMapping("/remove")
    public String removeFromCart(@AuthenticationPrincipal CurrentUser currentUser, @RequestParam("productId") int productId) {
        cartService.remove(currentUser.getUser().getId(),productId);
        return "redirect:/cart";
    }
}
