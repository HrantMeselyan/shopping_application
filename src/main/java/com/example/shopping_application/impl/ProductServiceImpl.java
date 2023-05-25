package com.example.shopping_application.impl;

import com.example.shopping_application.entity.Product;
import com.example.shopping_application.repository.ProductRepository;
import com.example.shopping_application.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by Ashot Simonyan on 21.05.23.
 */
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public void remove(int id) {
        productRepository.deleteById(id);
    }

    @Override
    public void save(Product product) {
        productRepository.save(product);
    }

    @Override
    public Product findByUserId(int id) {
        Optional<Product> allByUserId = productRepository.findAllByUser_Id(id);
        Product product = allByUserId.get();
        return product;
    }
}
