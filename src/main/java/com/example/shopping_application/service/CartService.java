package com.example.shopping_application.service;

import com.example.shopping_application.entity.Cart;
import com.example.shopping_application.security.CurrentUser;

import java.util.List;

public interface CartService {
    List<Cart> findAllByUser_id(int id);

    void save(int id, CurrentUser currentUser);

    void remove(int id,int productId);
}
