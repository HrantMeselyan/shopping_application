package com.example.shopping_application.service;

import com.example.shopping_application.entity.WishList;

import java.util.List;

public interface WishListService {

    List<WishList> findAll();

    void remove(int id);

    void save(WishList wishList);
}
