package com.example.shopping_application.dto.orderDto;

import com.example.shopping_application.dto.productDto.CreateProductRequestDto;
import com.example.shopping_application.dto.productDto.ProductDto;
import com.example.shopping_application.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class OrderItemDto {
    private int count;
    private CreateProductRequestDto product;
    private OrderDto order;
}
