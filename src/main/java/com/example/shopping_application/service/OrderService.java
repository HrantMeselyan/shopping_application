package com.example.shopping_application.service;

import com.example.shopping_application.dto.orderDto.OrderResponseDto;
import com.example.shopping_application.entity.Order;
import com.example.shopping_application.entity.OrderItem;
import com.example.shopping_application.entity.Status;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    List<OrderResponseDto> findAllOrder();

    Optional<Order> findByUserIdAndStatus(int id, Status status);

    void removeByProductIdAndOrderItemId(int product_id, int orderItem_id,int userId);
    void checkOrderItem(int userId);

    void save(int userId);
}
