package com.example.shopping_application.controller;

import com.example.shopping_application.entity.Notification;
import com.example.shopping_application.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/notification")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @PostMapping("/add")
    public String addNotification(@ModelAttribute Notification notification) {
        notificationService.save(notification);
        return "redirect:/notificationPage/" + notification.getId();
    }

    @GetMapping("/remove")
    public String removeNotification(@RequestParam("id") int id) {
        notificationService.remove(id);
        return "redirect:/notificationPage/";
    }
}
