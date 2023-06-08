package com.example.shopping_application.controller;

import com.example.shopping_application.entity.Order;
import com.example.shopping_application.security.CurrentUser;
import com.example.shopping_application.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;


    @GetMapping
    public String orderPage(ModelMap modelMap) {
        modelMap.addAttribute("orders", orderService.findAllOrder());
        return "checkout";
    }

    @PostMapping("/add")
    public String addOrder(@AuthenticationPrincipal CurrentUser currentUser) {
        orderService.save(currentUser.getUser().getId());
        return "redirect:/cart";
    }

    @GetMapping("/remove")
    public String removeComment(@RequestParam("id") int id, @RequestParam("productId") int productId) {
        orderService.remove(id);
        return "redirect:/order/" + productId;
    }
}
