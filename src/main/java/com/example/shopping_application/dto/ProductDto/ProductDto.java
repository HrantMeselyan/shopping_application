package com.example.shopping_application.dto.ProductDto;

import com.example.shopping_application.entity.Category;
import com.example.shopping_application.entity.Image;
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
    private List<Image> images;
    private List<Category> categories;
}
