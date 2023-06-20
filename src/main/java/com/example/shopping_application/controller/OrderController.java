package com.example.shopping_application.controller;

import com.example.shopping_application.entity.Address;
import com.example.shopping_application.entity.Order;
import com.example.shopping_application.entity.Status;
import com.example.shopping_application.mapper.OrderMapper;
import com.example.shopping_application.mapper.UserMapper;
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

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;


    @GetMapping
    public String orderPage(ModelMap modelMap,
                            @AuthenticationPrincipal CurrentUser currentUser) {
        List<Address> addresses = UserMapper.currentUserToUser(currentUser).getAddresses();
        Optional<Order> byUserIdAndStatus = orderService
                .findByUserIdAndStatus(currentUser.getUser().getId(), Status.PENDING);
        modelMap.addAttribute("order", OrderMapper.orderToOrderDto(byUserIdAndStatus.orElse(null)));
        return "checkout";
    }

    @PostMapping("/add")
    public String addOrder(@AuthenticationPrincipal CurrentUser currentUser) {
        orderService.save(currentUser.getUser().getId());
        return "redirect:/cart";
    }

    @GetMapping("/remove")
    public String removeProduct(@RequestParam("product_id") int product_id,
                                @RequestParam("orderItem_id") int orderItem_id,
                                @AuthenticationPrincipal CurrentUser currentUser) {
        orderService.removeByProductIdAndOrderItemId(product_id,orderItem_id, UserMapper.currentUserToUser(currentUser).getId());
        return "redirect:/order";
    }
}
