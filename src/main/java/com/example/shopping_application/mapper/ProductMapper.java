package com.example.shopping_application.mapper;

import com.example.shopping_application.dto.ProductDto.CreateProductRequestDto;
import com.example.shopping_application.dto.ProductDto.CreateProductResponseDto;
import com.example.shopping_application.dto.ProductDto.ProductDto;
import com.example.shopping_application.entity.Category;
import com.example.shopping_application.entity.Image;
import com.example.shopping_application.entity.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductMapper {
    public static Product map(CreateProductRequestDto dto) {
        if (dto == null) {
            return null;
        }

        Product product = new Product();

        product.setName(dto.getName());
        product.setProductCode(dto.getProductCode());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        List<Image> list = dto.getImages();
        if (list != null) {
            product.setImages(new ArrayList<Image>(list));
        }
        List<Category> list1 = dto.getCategories();
        if (list1 != null) {
            product.setCategories(new ArrayList<Category>(list1));
        }

        return product;
    }

    public static CreateProductResponseDto map(Product entity) {
        if (entity == null) {
            return null;
        }

        CreateProductResponseDto createProductResponseDto = new CreateProductResponseDto();

        createProductResponseDto.setId(entity.getId());
        createProductResponseDto.setName(entity.getName());
        createProductResponseDto.setProductCode(entity.getProductCode());
        createProductResponseDto.setDescription(entity.getDescription());
        createProductResponseDto.setPrice(entity.getPrice());
        List<Image> list = entity.getImages();
        if (list != null) {
            createProductResponseDto.setImages(new ArrayList<Image>(list));
        }
        List<Category> list1 = entity.getCategories();
        if (list1 != null) {
            createProductResponseDto.setCategories(new ArrayList<Category>(list1));
        }

        return createProductResponseDto;
    }

    public static ProductDto mapToDto(Product entity) {
        if (entity == null) {
            return null;
        }

        ProductDto productDto = new ProductDto();

        productDto.setId(entity.getId());
        productDto.setName(entity.getName());
        productDto.setProductCode(entity.getProductCode());
        productDto.setDescription(entity.getDescription());
        productDto.setPrice(entity.getPrice());
        List<Image> list = entity.getImages();
        if (list != null) {
            productDto.setImages(new ArrayList<Image>(list));
        }
        List<Category> list1 = entity.getCategories();
        if (list1 != null) {
            productDto.setCategories(new ArrayList<Category>(list1));
        }

        return productDto;
    }
}

