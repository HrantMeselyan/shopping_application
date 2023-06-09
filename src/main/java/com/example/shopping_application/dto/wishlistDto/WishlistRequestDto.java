package com.example.shopping_application.dto.wishlistDto;

import com.example.shopping_application.dto.productDto.CreateProductResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WishlistRequestDto {
    private Set<CreateProductResponseDto> product;
}
