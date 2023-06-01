package com.example.shopping_application.service;

import com.example.shopping_application.entity.Image;
import com.example.shopping_application.entity.Product;
import com.example.shopping_application.security.CurrentUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductService {

    Page<Product> findAllProducts(Pageable pageable);

    void remove(int id);


    void save(Product product, MultipartFile multipartFile, CurrentUser currentUser,Image image) throws IOException;

    Product findByUserId(int id);

    Product findById(int id);
}
