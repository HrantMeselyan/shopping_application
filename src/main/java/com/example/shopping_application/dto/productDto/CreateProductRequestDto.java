package com.example.shopping_application.dto.productDto;

import com.example.shopping_application.dto.categoryDto.CategoryDto;
import com.example.shopping_application.dto.imageDto.ImageDto;
import com.example.shopping_application.entity.Category;
import com.example.shopping_application.entity.Image;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductRequestDto {

    private String name;
    private String productCode;
    private String description;
    private int count;
    private double price;
    private List<ImageDto> images;
    private List<CategoryDto> categories;
}
