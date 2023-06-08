package com.example.shopping_application.mapper;

import com.example.shopping_application.dto.notificationDto.NotificationRequestDto;
import com.example.shopping_application.dto.notificationDto.NotificationResponseDto;
import com.example.shopping_application.entity.Notification;
import com.example.shopping_application.entity.User;

import java.util.ArrayList;
import java.util.List;

public class NotificationMapper {
    public static Notification map(NotificationRequestDto notificationRequestDto, User user) {
        Notification notification = new Notification();
        notification.setUser(user);
        notification.setMessage(notification.getMessage());
        return notification;
    }

    public static List<NotificationResponseDto> map(List<Notification> notifications) {
        List<NotificationResponseDto> notificationResponseDtoList = new ArrayList<>();
        NotificationResponseDto notificationResponseDto = new NotificationResponseDto();
        for (Notification notification : notifications) {
            notificationResponseDto.setMessage(notification.getMessage());
            notificationResponseDtoList.add(notificationResponseDto);
        }
        return notificationResponseDtoList;
    }

}
