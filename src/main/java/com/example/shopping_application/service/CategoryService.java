package com.example.shopping_application.service;

import com.example.shopping_application.entity.Category;

import java.util.List;

public interface CategoryService {

    List<Category> findAllCategory();
    void remove(int id);
    void save(Category category);
}
