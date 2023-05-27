package com.example.shopping_application.service;

import com.example.shopping_application.entity.Product;
import com.example.shopping_application.security.CurrentUser;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    List<Product> findAllProducts();

    void remove(int id);

    void save(Product product);

    void save(Product product, MultipartFile multipartFile, CurrentUser currentUser) throws IOException;

    Product findByUserId(int id);
    Product findById(int id);
}
