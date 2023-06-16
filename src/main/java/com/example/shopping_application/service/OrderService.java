package com.example.shopping_application.service;

import com.example.shopping_application.dto.orderDto.OrderResponseDto;
import com.example.shopping_application.entity.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    List<OrderResponseDto> findAllOrder();

    Order  findByUserId(int id);

    void remove(int id);

    void save(int userId);
}
