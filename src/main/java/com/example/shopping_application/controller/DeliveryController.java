package com.example.shopping_application.controller;


import com.example.shopping_application.entity.Delivery;
import com.example.shopping_application.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/delivery")
@RequiredArgsConstructor
public class DeliveryController {
    private final DeliveryService deliveryService;

    @PostMapping("/add")
    public String addDelivery(@ModelAttribute Delivery delivery) {
        deliveryService.save(delivery);
        return "redirect:/";
    }

    @GetMapping("/remove")
    public String removeDelivery(@RequestParam("id") int id, @RequestParam("ProductId") int productId) {
        deliveryService.remove(id);
        return "redirect:/products" + productId;
    }
}
