package com.example.shopping_application.dto.orderDto;

import com.example.shopping_application.dto.productDto.ProductDto;
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
    private ProductDto product;
}
