package com.example.shopping_application.service;

import com.example.shopping_application.dto.orderDto.OrderResponseDto;
import com.example.shopping_application.entity.Order;

import java.util.List;

public interface OrderService {

    List<OrderResponseDto> findAllOrder();

    List<Order>  findByUserId(int id);

    void remove(int id);

    void save(int userId);
}
