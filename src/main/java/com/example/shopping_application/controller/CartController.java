package com.example.shopping_application.controller;

import com.example.shopping_application.dto.cartDto.UpdateCartItemRequestDto;
import com.example.shopping_application.security.CurrentUser;
import com.example.shopping_application.service.CartService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public String removeFromCart(@RequestParam("countRemove") int count, @AuthenticationPrincipal CurrentUser currentUser, @RequestParam("productRemoveId") int productId) {
        cartService.remove(currentUser.getUser().getId(), productId, count);
        return "redirect:/cart";
    }

    @PostMapping("/update")
    public String updateCartItemCounts(@RequestBody UpdateCartItemRequestDto request) {
        List<Integer> counts = request.getCount();
        List<Integer> cartItemIds = request.getCartItem();
        boolean isValid = cartService.updateCartItemCounts(cartItemIds, counts);
        return "redirect:/cart";
    }

}
