package com.example.shopping_application.impl;

import com.example.shopping_application.entity.Moderate;
import com.example.shopping_application.entity.Product;
import com.example.shopping_application.entity.UserType;
import com.example.shopping_application.repository.ProductRepository;
import com.example.shopping_application.security.CurrentUser;
import com.example.shopping_application.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Created by Ashot Simonyan on 21.05.23.
 */
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    @Value("${shopping-app.upload.image.path}")
    private String imageUploadPath;

    private final ProductRepository productRepository;

    @Override
    public Page<Product> findAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
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
    public void save(Product product, MultipartFile multipartFile, CurrentUser currentUser) throws IOException {
        product.getCategories().removeIf(category -> category.getId() == 0);
        if (currentUser != null) {
            product.setUser(currentUser.getUser());
            if (currentUser.getUser().getUserType() != UserType.USER) {
                product.setModerate(Moderate.TRUE);
            } else {
                product.setModerate(Moderate.FALSE);
            }
            if (multipartFile != null && !multipartFile.isEmpty()) {
                String fileName = System.nanoTime() + "_" + multipartFile.getOriginalFilename();
                File file = new File(imageUploadPath + fileName);
                multipartFile.transferTo(file);
                product.setProfilePic(fileName);
            }
            productRepository.save(product);
        }
    }

    @Override
    public Product findByUserId(int id) {
        Optional<Product> allByUserId = productRepository.findAllByUser_Id(id);
        if (allByUserId.isPresent()) {
            return allByUserId.get();
        }
        return null;
    }

    @Override
    public Product findById(int id) {
        Optional<Product> byId = productRepository.findById(id);
        if (byId.isPresent()) {
            return byId.get();
        }
        return null;
    }
}
