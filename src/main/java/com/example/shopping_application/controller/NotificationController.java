package com.example.shopping_application.controller;

import com.example.shopping_application.dto.notificationDto.NotificationRequestDto;
import com.example.shopping_application.security.CurrentUser;
import com.example.shopping_application.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/notification")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping()
    public String notificationPage(ModelMap modelMap,@AuthenticationPrincipal CurrentUser currentUser) {
        modelMap.addAttribute("notifications",notificationService.findAllByUserId(currentUser.getUser().getId()));
        return "notifications";
    }

    @PostMapping("/add")
    public String addNotification(@ModelAttribute NotificationRequestDto notificationRequestDto, @AuthenticationPrincipal CurrentUser currentUser) {
        notificationService.save(notificationRequestDto, currentUser.getUser());
        return "redirect:/notificationPage";
    }

    @GetMapping("/remove")
    public String removeNotification(@RequestParam("id") int id) {
        notificationService.remove(id);
        return "redirect:/notificationPage";
    }
}
