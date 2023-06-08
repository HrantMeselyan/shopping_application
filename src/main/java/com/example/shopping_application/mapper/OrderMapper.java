package com.example.shopping_application.mapper;

import com.example.shopping_application.dto.orderDto.OrderItemDto;
import com.example.shopping_application.dto.orderDto.OrderResponseDto;
import com.example.shopping_application.entity.Order;
import com.example.shopping_application.entity.OrderItem;

import java.util.ArrayList;
import java.util.List;

public class OrderMapper {
    public static List<OrderResponseDto> findAll(List<Order> all) {
        List<OrderResponseDto> orderDtoList = new ArrayList<>();
        for (Order order : all) {
            List<OrderItemDto> orderItemsDto = new ArrayList<>();
            for (OrderItem orderItem : order.getOrderItems()) {
                OrderItemDto orderItemDto = new OrderItemDto();
                orderItemDto.setProduct(ProductMapper.mapToDto(orderItem.getProduct()));
                orderItemDto.setCount(orderItem.getCount());
                orderItemsDto.add(orderItemDto);
            }
            orderDtoList.add(OrderResponseDto.builder()
                    .dateTime(order.getDateTime())
                    .totalAmount(order.getTotalAmount())
                    .orderItems(orderItemsDto)
                    .dateTime(order.getDateTime())
                    .build());
        }
        return orderDtoList;
    }

}
