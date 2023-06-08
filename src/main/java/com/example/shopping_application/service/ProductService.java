package com.example.shopping_application.service;

import com.example.shopping_application.dto.ProductDto.CreateProductRequestDto;
import com.example.shopping_application.entity.Product;
import com.example.shopping_application.security.CurrentUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    Page<Product> findAllProducts(Pageable pageable);
    List<Product> findAll();

    void remove(int id);


    void save(CreateProductRequestDto product, MultipartFile[] files, CurrentUser currentUser) throws IOException;

    Product findByUserId(int id);

    Product findById(int id);
}
