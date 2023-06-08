package com.example.shopping_application.mapper;

import com.example.shopping_application.dto.cartDto.CartDto;
import com.example.shopping_application.dto.cartDto.CartItemDto;
import com.example.shopping_application.entity.Cart;
import com.example.shopping_application.entity.CartItem;

import java.util.ArrayList;
import java.util.List;

public class CartMapper {
    public static CartDto convertToDto(Cart cart) {
        CartDto cartDto = new CartDto();
        cartDto.setUserId(cart.getUser().getId());

        List<CartItemDto> cartItemDTOs = new ArrayList<>();
        for (CartItem cartItem : cart.getCartItems()) {
            CartItemDto cartItemDto = new CartItemDto();
            cartItemDto.setCount(cartItem.getCount());
            cartItemDto.setProductId(cartItem.getProduct().getId());
            cartItemDTOs.add(cartItemDto);
        }
        cartDto.setCartItems(cartItemDTOs);

        return cartDto;
    }

    public static List<CartDto> findAllByUser_id(List<Cart> allByUserId) {

        List<CartDto> cartDtoList = new ArrayList<>();
        for (Cart cart : allByUserId) {
            CartDto cartDto = new CartDto();
            cartDto.setId(cart.getId());
            cartDto.setUserId(cart.getUser().getId());

            List<CartItemDto> cartItemDtoList = new ArrayList<>();
            for (CartItem cartItem : cart.getCartItems()) {
                CartItemDto cartItemDto = new CartItemDto();
                cartItemDto.setId(cartItem.getId());
                cartItemDto.setCount(cartItem.getCount());
                cartItemDto.setProductId(cartItem.getProduct().getId());
                cartItemDtoList.add(cartItemDto);
            }
            cartDto.setCartItems(cartItemDtoList);

            cartDtoList.add(cartDto);
        }

        return cartDtoList;
    }

}
