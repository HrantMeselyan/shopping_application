package com.example.shopping_application.mapper;

import com.example.shopping_application.dto.wishlistDto.WishlistResponseDto;
import com.example.shopping_application.entity.WishList;

public class WishListMapper {
    public static WishlistResponseDto map(WishList wishlist) {
        if (wishlist == null) {
            return null;
        }
        WishlistResponseDto wishlistResponseDto = new WishlistResponseDto();
        wishlistResponseDto.setProduct(ProductMapper.mapToDto(wishlist.getProduct()));
        return wishlistResponseDto;
    }
}