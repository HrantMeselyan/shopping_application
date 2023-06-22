package com.example.shopping_application.service;

import com.example.shopping_application.dto.cartDto.CartDto;
import com.example.shopping_application.dto.cartDto.CartItemDto;
import com.example.shopping_application.security.CurrentUser;

import java.util.List;

public interface CartService {

    List<CartItemDto> findLastCartItemsByLimit(int userId);

    CartDto findAllByUser_id(int id);

    void save(int id, CurrentUser currentUser);

    void remove(int id, int productId, int count);

    boolean updateCartItemCounts(List<Integer> cartItemIds, List<Integer> counts);
}
