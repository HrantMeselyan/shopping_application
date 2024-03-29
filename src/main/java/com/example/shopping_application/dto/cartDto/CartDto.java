package com.example.shopping_application.dto.cartDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {
    private int id;
    private int userId;
    private List<CartItemDto> cartItems;
}
