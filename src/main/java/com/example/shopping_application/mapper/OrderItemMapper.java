package com.example.shopping_application.mapper;

import com.example.shopping_application.dto.orderDto.OrderItemDto;
import com.example.shopping_application.entity.OrderItem;

/**
 * Created by Ashot Simonyan on 10.06.23.
 */

public class OrderItemMapper {

    public static OrderItem orderItemDtoToOrderItem(OrderItemDto orderItemDto) {
        if (orderItemDto == null) {
            return null;
        }
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(OrderMapper.orderDtoToOrder(orderItemDto.getOrder()));
        orderItem.setProduct(ProductMapper.map(orderItemDto.getProduct()));
        orderItem.setCount(orderItemDto.getCount());
        return orderItem;
    }

    public static OrderItemDto orderItemToOrderItemDto(OrderItem orderItem) {
        if (orderItem == null) {
            return null;
        }
        return OrderItemDto.builder()
                .order(OrderMapper.orderToOrderDto(orderItem.getOrder()))
                .count(orderItem.getCount())
                .product(ProductMapper.mapToRequestDto(orderItem.getProduct()))
                .build();
    }
}
