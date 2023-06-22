package com.example.shopping_application.controller;


import com.example.shopping_application.entity.Delivery;
import com.example.shopping_application.entity.Status;
import com.example.shopping_application.mapper.UserMapper;
import com.example.shopping_application.security.CurrentUser;
import com.example.shopping_application.service.DeliveryService;
import com.example.shopping_application.service.OrderService;
import com.example.shopping_application.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/delivery")
@RequiredArgsConstructor
public class DeliveryController {
    private final DeliveryService deliveryService;
    private final UserService userService;
    private final OrderService orderService;

    @GetMapping("/add")
    public String addDelivery(@RequestParam("order_id") int id) {
        deliveryService.save(id);
        return "redirect:/";
    }

    @GetMapping("/remove")
    public String removeDelivery(@RequestParam("id") int id, @RequestParam("ProductId") int productId) {
        deliveryService.remove(id);
        return "redirect:/products" + productId;
    }

    @GetMapping("/customer")
    public String deliveryUserPage(ModelMap modelmap,
                                   @RequestParam("id") int id) {
        modelmap.addAttribute("user", userService.findByIdWithAddresses(id));
        return "singleDeliveryUserPage";
    }

    @GetMapping("/order")
    public String deliveryOrderPage(ModelMap modelmap,
                                    @AuthenticationPrincipal CurrentUser currentUser,
                                    @RequestParam("id") int id) {
        modelmap.addAttribute("user", userService.findByIdWithAddresses(UserMapper.currentUserToUser(currentUser).getId()));
        modelmap.addAttribute("order",orderService.findById(id).orElse(null));
        return "singleDeliveryOrderPage";
    }
    @GetMapping("")
    public String deliveryPage(@AuthenticationPrincipal CurrentUser currentUser,
                               @RequestParam("page") Optional<Integer> page,
                               @RequestParam("size") Optional<Integer> size,
                               ModelMap modelMap) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(9);
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
        Page<Delivery> result = deliveryService.findAllByOrderStatus(Status.APPROVED, pageable);
        int totalPages = result.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .toList();
            modelMap.addAttribute("pageNumbers", pageNumbers);
        }
        modelMap.addAttribute("totalPages", totalPages);
        modelMap.addAttribute("currentPage", currentPage);
        modelMap.addAttribute("deliveries1", result);
        modelMap.addAttribute("user", UserMapper.currentUserToUser(currentUser));
        modelMap.addAttribute("deliveries2",
                deliveryService.findAllByUserIdAndOrderStatus(UserMapper.currentUserToUser(currentUser).getId(), Status.IN_PROCESS, pageable).getContent());
        return "account-delivery";
    }

    @GetMapping("/custom")
    public String customDeliveryPage(@AuthenticationPrincipal CurrentUser currentUser,
                                     @RequestParam("delivery_id") int id,
                                     @RequestParam("page") Optional<Integer> page,
                                     @RequestParam("size") Optional<Integer> size,
                                     ModelMap modelMap) {
        deliveryService.chooseDelivery(UserMapper.currentUserToUser(currentUser), id,Status.IN_PROCESS);
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(9);
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
        Page<Delivery> result = deliveryService.findAllByOrderStatus(Status.APPROVED, pageable);
        int totalPages = result.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .toList();
            modelMap.addAttribute("pageNumbers", pageNumbers);
        }
        modelMap.addAttribute("totalPages", totalPages);
        modelMap.addAttribute("currentPage", currentPage);
        modelMap.addAttribute("deliveries1", result);
        modelMap.addAttribute("user", UserMapper.currentUserToUser(currentUser));
        modelMap.addAttribute("deliveries2",
                deliveryService.findAllByUserIdAndOrderStatus(UserMapper.currentUserToUser(currentUser).getId(), Status.IN_PROCESS, pageable).getContent());
        return "account-delivery";
    }


    @GetMapping("/inProcess")
    public String inProcessDeliveryPage(@AuthenticationPrincipal CurrentUser currentUser,
                                        @RequestParam("page") Optional<Integer> page,
                                        @RequestParam("size") Optional<Integer> size,
                                        ModelMap modelMap) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(9);
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
        Page<Delivery> result = deliveryService.findAllByUserIdAndOrderStatus(UserMapper.currentUserToUser(currentUser).getId(), Status.IN_PROCESS, pageable);
        int totalPages = result.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .toList();
            modelMap.addAttribute("pageNumbers", pageNumbers);
        }
        modelMap.addAttribute("totalPages", totalPages);
        modelMap.addAttribute("currentPage", currentPage);
        modelMap.addAttribute("deliveries2", result);


        modelMap.addAttribute("user", UserMapper.currentUserToUser(currentUser));
        modelMap.addAttribute("deliveries1",
                deliveryService.findAllByOrderStatus(Status.APPROVED, pageable).getContent());
        return "account-delivery-custom";
    }

    @GetMapping("/inProcess/custom")
    public String customInProcessDeliveryPage(@AuthenticationPrincipal CurrentUser currentUser,
                                              @RequestParam("delivery_id") int id,
                                              @RequestParam("page") Optional<Integer> page,
                                              @RequestParam("size") Optional<Integer> size,
                                              ModelMap modelMap) {
        deliveryService.chooseDelivery(UserMapper.currentUserToUser(currentUser), id,Status.DELIVERED);
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(9);
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
        Page<Delivery> result = deliveryService.findAllByUserIdAndOrderStatus(UserMapper.currentUserToUser(currentUser).getId(), Status.IN_PROCESS, pageable);
        int totalPages = result.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .toList();
            modelMap.addAttribute("pageNumbers", pageNumbers);
        }
        modelMap.addAttribute("totalPages", totalPages);
        modelMap.addAttribute("currentPage", currentPage);
        modelMap.addAttribute("deliveries2", result);


        modelMap.addAttribute("user", UserMapper.currentUserToUser(currentUser));
        modelMap.addAttribute("deliveries1",
                deliveryService.findAllByOrderStatus(Status.APPROVED, pageable).getContent());
        return "account-delivery-custom";
    }
}
