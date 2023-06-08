package com.example.shopping_application.mapper;

import com.example.shopping_application.dto.ProductDto.CreateProductRequestDto;
import com.example.shopping_application.dto.ProductDto.CreateProductResponseDto;
import com.example.shopping_application.dto.ProductDto.ProductDto;
import com.example.shopping_application.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product map(CreateProductRequestDto dto);

    CreateProductResponseDto map(Product entity);

    ProductDto mapToDto(Product entity);
}

