package com.example.shopping_application.mapper;

import com.example.shopping_application.dto.categoryDto.CategoryDto;
import com.example.shopping_application.entity.Category;

/**
 * Created by Ashot Simonyan on 08.06.23.
 */

public class CategoryMapper {

    public static Category dtoToCategory(CategoryDto categoryDto) {
        if (categoryDto == null) {
            return null;
        }
        Category category = new Category();
        category.setName(categoryDto.getName());
        category.setParentCategory(categoryDto.getParentCategory());
        category.setImage(categoryDto.getImage());
        return category;
    }

    public static CategoryDto categoryToDto(Category category) {
        if (category == null) {
            return null;
        }
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName(category.getName());
        categoryDto.setParentCategory(category.getParentCategory());
        categoryDto.setImage(category.getImage());
        return categoryDto;
    }
}
