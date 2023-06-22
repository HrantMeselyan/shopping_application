package com.example.shopping_application.dto.productDto;

import com.example.shopping_application.dto.categoryDto.CategoryDto;
import com.example.shopping_application.dto.imageDto.ImageDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private int id;
    private String name;
    private String productCode;
    private String description;
    private double price;
    private List<ImageDto> images;
    private List<CategoryDto> categories;
}
