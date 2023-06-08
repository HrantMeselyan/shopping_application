package com.example.shopping_application.service.impl;

import com.example.shopping_application.dto.ProductDto.CreateProductRequestDto;
import com.example.shopping_application.entity.Image;
import com.example.shopping_application.entity.Product;
import com.example.shopping_application.mapper.ProductMapper;
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
import java.util.ArrayList;
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
    private final ProductMapper productMapper;


    @Override
    public Page<Product> findAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public void remove(int id) {
        productRepository.deleteById(id);
    }


    @Override
    public void save(CreateProductRequestDto productRequestDto, MultipartFile[] files, CurrentUser currentUser) throws IOException {
        Product product = productMapper.map(productRequestDto);
        product.getCategories().removeIf(category -> category.getId() == 0);
        List<Image> imageList = new ArrayList<>();
        if (currentUser != null) {
            product.setUser(currentUser.getUser());
            for (MultipartFile multipartFile : files) {
                if (multipartFile != null && !multipartFile.isEmpty()) {
                    String fileName = System.nanoTime() + "_" + multipartFile.getOriginalFilename();
                    File file = new File(imageUploadPath + fileName);
                    multipartFile.transferTo(file);
                    Image image = new Image();
                    image.setImage(fileName);
                    imageList.add(image);
                }
            }
            product.setImages(imageList);
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
