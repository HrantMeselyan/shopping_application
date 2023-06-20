package com.example.shopping_application.service.impl;

import com.example.shopping_application.dto.productDto.CreateProductRequestDto;
import com.example.shopping_application.dto.productDto.CreateProductResponseDto;
import com.example.shopping_application.entity.Image;
import com.example.shopping_application.entity.Product;
import com.example.shopping_application.mapper.ProductMapper;
import com.example.shopping_application.mapper.UserMapper;
import com.example.shopping_application.repository.ProductRepository;
import com.example.shopping_application.security.CurrentUser;
import com.example.shopping_application.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    @Override
    public Page<Product> findAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Page<Product> findByName(String name,Pageable pageable) {
        return productRepository.findByNameContainingIgnoreCase(name, pageable);
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
    @Transactional
    public void save(CreateProductRequestDto productRequestDto, MultipartFile[] files, CurrentUser currentUser) throws IOException {
        Product product = ProductMapper.map(productRequestDto);
        product.getCategories().removeIf(category -> category.getId() == 0);
        List<Image> imageList = new ArrayList<>();
        product.setUser(UserMapper.currentUserToUser(currentUser));
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

    @Override
    public Product findBy_Id(int id) {
        Optional<Product> byId = productRepository.findById(id);
        return byId.orElse(null);
    }

    @Override
    public CreateProductResponseDto findById(int id) {
        Optional<Product> byId = productRepository.findById(id);
        return ProductMapper.mapToResponseDto(byId.orElse(null));
    }

}
