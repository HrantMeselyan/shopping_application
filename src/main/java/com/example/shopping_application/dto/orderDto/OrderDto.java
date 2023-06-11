package com.example.shopping_application.dto.orderDto;

import com.example.shopping_application.entity.OrderItem;
import com.example.shopping_application.entity.Status;
import com.example.shopping_application.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDto {

    private int id;
    private LocalDateTime dateTime;
    private double totalAmount;
    private Status status;
    private User user;
    private List<OrderItemDto> orderItems;
}
