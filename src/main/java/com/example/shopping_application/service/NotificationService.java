package com.example.shopping_application.service;

import com.example.shopping_application.dto.notificationDto.NotificationRequestDto;
import com.example.shopping_application.dto.notificationDto.NotificationResponseDto;
import com.example.shopping_application.entity.Notification;
import com.example.shopping_application.entity.User;

import java.util.List;

public interface NotificationService {

    List<NotificationResponseDto> findAllByUserId(int id);

    void remove(int id);

    void save(NotificationRequestDto notificationRequestDto, User user);
}
