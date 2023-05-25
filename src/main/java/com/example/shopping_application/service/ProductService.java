package com.example.shopping_application.service;

import com.example.shopping_application.entity.Product;

import java.util.List;

public interface ProductService {

    List<Product> findAllProducts();
    void remove(int id);
    void save(Product product);

    Product findByUserId(int id);
}
