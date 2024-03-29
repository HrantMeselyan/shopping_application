package com.example.shopping_application.mapper;

import com.example.shopping_application.dto.cartDto.CartDto;
import com.example.shopping_application.dto.cartDto.CartItemDto;
import com.example.shopping_application.dto.productDto.ProductDto;
import com.example.shopping_application.entity.Cart;
import com.example.shopping_application.entity.CartItem;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CartMapper {
    public static CartDto convertToDto(Cart cart) {
        CartDto cartDto = new CartDto();
        cartDto.setUserId(cart.getUser().getId());

        List<CartItemDto> cartItemDTOs = new ArrayList<>();
        for (CartItem cartItem : cart.getCartItems()) {
            CartItemDto cartItemDto = new CartItemDto();
            cartItemDto.setId(cartItem.getId());
            cartItemDto.setCount(cartItem.getCount());
            ProductDto productDto = ProductMapper.mapToDto(cartItem.getProduct());
            cartItemDto.setProduct(productDto);
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
                cartItemDto.setCount(cartItem.getCount());
                cartItemDto.setId(cartItem.getId());
                ProductDto productDto = ProductMapper.mapToDto(cartItem.getProduct());
                cartItemDto.setProduct(productDto);
                cartItemDtoList.add(cartItemDto);
            }
            cartDto.setCartItems(cartItemDtoList);

            cartDtoList.add(cartDto);
        }

        return cartDtoList;
    }
    public static List<CartItemDto> mapToDtoList(List<CartItem> cartItems) {
        return cartItems.stream()
                .map(CartMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public static CartItemDto mapToDto(CartItem cartItem) {
        CartItemDto cartItemDto = new CartItemDto();
        cartItemDto.setId(cartItem.getId());
        cartItemDto.setCount(cartItem.getCount());
        cartItemDto.setProduct(ProductMapper.mapToDto(cartItem.getProduct()));
        return cartItemDto;
    }


}
