package com.example.shopping_application.service;

import com.example.shopping_application.entity.Delivery;
import com.example.shopping_application.entity.Status;
import com.example.shopping_application.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface DeliveryService {

    Page<Delivery> findAllByUserIdAndOrderStatus(int id, Status status, Pageable pageable);
    Page<Delivery> findAllByOrderStatus(Status status, Pageable pageable);

    Optional<Delivery> findById(int id);

    void remove(int id);

    void save(int id);
    void save(Delivery delivery);

    void chooseDelivery(User user, int id,Status status);
}
