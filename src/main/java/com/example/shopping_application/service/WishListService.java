package com.example.shopping_application.service;

import com.example.shopping_application.entity.Product;
import com.example.shopping_application.entity.WishList;

import java.util.List;
import java.util.Optional;

public interface WishListService {

    List<WishList> findAll();

    List<WishList> findAllByUserId(int id);

    void remove(int id);
    void removeByProductId(int id);

    void save(WishList wishList);

    Optional<WishList> findByProduct(Product product);
}
