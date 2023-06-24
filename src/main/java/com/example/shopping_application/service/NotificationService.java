package com.example.shopping_application.service;

import com.example.shopping_application.dto.notificationDto.NotificationRequestDto;
import com.example.shopping_application.dto.notificationDto.NotificationResponseDto;

import java.util.List;

public interface NotificationService {

    List<NotificationResponseDto> findAllByUserId(int id);

    void remove(int id);

    void save(NotificationRequestDto notificationRequestDto);
}
