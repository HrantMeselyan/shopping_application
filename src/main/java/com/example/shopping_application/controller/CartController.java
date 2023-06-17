package com.example.shopping_application.controller;

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
    public String updateCartItemCounts(@RequestParam("count") String countJson,
                                       @RequestParam("cartItem") String cartItemJson) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<Integer> counts = objectMapper.readValue(countJson, new TypeReference<List<Integer>>() {
            });
            List<Integer> cartItemIds = objectMapper.readValue(cartItemJson, new TypeReference<List<Integer>>() {
            });

            cartService.updateCartItemCounts(cartItemIds, counts);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "redirect:/cart";
    }

}
