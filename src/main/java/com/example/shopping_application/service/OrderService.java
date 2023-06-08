package com.example.shopping_application.service;

import com.example.shopping_application.dto.orderDto.OrderResponseDto;

import java.util.List;

public interface OrderService {

    List<OrderResponseDto> findAllOrder();

    void remove(int id);

    void save(int userId);
}
