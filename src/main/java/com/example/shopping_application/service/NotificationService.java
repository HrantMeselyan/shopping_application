package com.example.shopping_application.service;

import com.example.shopping_application.entity.Notification;

import java.util.List;

public interface NotificationService {

    List<Notification> findAllCategory();
    void remove(int id);
    void save(Notification notification);
}
