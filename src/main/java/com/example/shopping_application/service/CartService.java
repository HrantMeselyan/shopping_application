package com.example.shopping_application.service;

import com.example.shopping_application.dto.cartDto.CartDto;
import com.example.shopping_application.entity.User;
import com.example.shopping_application.security.CurrentUser;

import java.util.List;

public interface CartService {

    List<CartDto> findAllByUser_id(int id);

    void save(int id, CurrentUser currentUser);

    void remove(int id, int productId, int count);

    void update(int count, int cartItemId);
}
