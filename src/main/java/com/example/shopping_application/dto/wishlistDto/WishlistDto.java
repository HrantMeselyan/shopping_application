package com.example.shopping_application.dto.wishlistDto;

import com.example.shopping_application.dto.productDto.ProductDto;
import com.example.shopping_application.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WishlistDto {
    private int id;
    private User user;
    private Set<ProductDto> product;
}
