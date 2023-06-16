package com.example.shopping_application.dto.cartDto;

import com.example.shopping_application.dto.productDto.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDto {
    private int id;
    private int count;
    private ProductDto product;
}
