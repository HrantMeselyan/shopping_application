package com.example.shopping_application.service;

import com.example.shopping_application.entity.Delivery;

import java.util.List;

public interface DeliveryService {

    List<Delivery> findAllCategory();
    void remove(int id);
    void save(Delivery delivery);
}
