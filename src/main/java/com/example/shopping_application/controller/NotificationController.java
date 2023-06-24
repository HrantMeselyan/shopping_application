package com.example.shopping_application.controller;

import com.example.shopping_application.dto.notificationDto.NotificationRequestDto;
import com.example.shopping_application.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/notification")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping("/send")
    public String sendNotificationPage() {
        return "admin/send-notification";
    }

    @PostMapping("/send")
    public String sendNotification(@ModelAttribute NotificationRequestDto notificationRequestDto) {
        notificationService.save(notificationRequestDto);
        return "redirect:/notification/send";
    }

    @GetMapping("/remove")
    public String removeNotification(@RequestParam("id") int id) {
        notificationService.remove(id);
        return "redirect:/notification/send";
    }
}
