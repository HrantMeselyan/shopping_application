package com.example.shopping_application.service;

import com.example.shopping_application.entity.User;

import java.util.List;

public interface UserService {

    List<User> findAllCategory();
    void remove(int id);
    void save(User user);
}
