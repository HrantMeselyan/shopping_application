package com.example.shopping_application.dto.cartDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCartItemRequestDto {
    private List<Integer> count;
    private List<Integer> cartItem;
}
