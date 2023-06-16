package com.example.shopping_application.service.impl;


import com.example.shopping_application.entity.Product;
import com.example.shopping_application.repository.ProductRepository;
import com.example.shopping_application.service.MainService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MainImpl implements MainService {
    private final ProductRepository productRepository;

    @Value("${shopping-app.upload.image.path}")
    private String imageUploadPath;

    @Override
    public byte[] getImage(String imageName) throws IOException {
        File file = new File(imageUploadPath + imageName);
        if (file.exists()) {
            FileInputStream fis = new FileInputStream(file);
            return IOUtils.toByteArray(fis);
        }
        return null;
    }

    @Override
    public List<Product> search(String value) {
        List<Product> byNameContainingIgnoreCase = productRepository.findByNameContainingIgnoreCase(value);
        return byNameContainingIgnoreCase;
    }
}