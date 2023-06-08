package com.example.shopping_application.service;

import com.example.shopping_application.entity.Product;
import com.example.shopping_application.entity.WishList;
import com.example.shopping_application.security.CurrentUser;

import java.util.List;
import java.util.Optional;

public interface WishListService {

    List<WishList> findAll();

    Optional<WishList> findByUserId(int id);

    void remove(int id);
    void remove(int id, CurrentUser currentUser);

    void save(WishList wishList);

    Optional<WishList> findByProduct(Product product);

    void save(int productId, CurrentUser currentUser);
}
