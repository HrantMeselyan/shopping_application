package com.example.shopping_application.controller;

import com.example.shopping_application.mapper.OrderMapper;
import com.example.shopping_application.mapper.UserMapper;
import com.example.shopping_application.repository.OrderItemRepository;
import com.example.shopping_application.security.CurrentUser;
import com.example.shopping_application.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final OrderItemRepository orderItemRepository;


    @GetMapping
    public String orderPage(ModelMap modelMap,
                            @AuthenticationPrincipal CurrentUser currentUser) {
        modelMap.addAttribute("orders", OrderMapper.listOrderToListOrderDto(orderService
                .findByUserId((UserMapper.currentUserToUser(currentUser)).getId())));
        return "checkout";
    }

    @PostMapping("/add")
    public String addOrder(@AuthenticationPrincipal CurrentUser currentUser) {
        orderService.save(currentUser.getUser().getId());
        return "redirect:/cart";
    }

    @GetMapping("/remove")
    public String removeProduct(@RequestParam("id") int id) {
        orderService.removeByProductId(id);
        return "redirect:/order";
    }
}
