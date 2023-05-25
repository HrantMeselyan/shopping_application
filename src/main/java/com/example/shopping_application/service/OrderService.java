package com.example.shopping_application.service;

import com.example.shopping_application.entity.Order;

import java.util.List;

public interface OrderService {

    List<Order> findAllOrder();

    void remove(int id);

    void save(Order order);
}
