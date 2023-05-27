package com.example.shopping_application.repository;

import com.example.shopping_application.entity.Comments;
import com.example.shopping_application.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    List<Notification> findAllByUser_Id(int Id);
}
