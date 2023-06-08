package com.example.shopping_application.dto.cartDto;

import com.example.shopping_application.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDto {
    private int count;
    private Product product;
}
