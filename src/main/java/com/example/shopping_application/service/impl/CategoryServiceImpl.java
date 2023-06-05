package com.example.shopping_application.service.impl;

import com.example.shopping_application.entity.Category;
import com.example.shopping_application.repository.CategoryRepository;
import com.example.shopping_application.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by Ashot Simonyan on 21.05.23.
 */
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Value("${shopping-app.upload.image.path}")
    private String imageUploadPath;

    @Override
    public List<Category> findAllCategory() {
        return categoryRepository.findAll();
    }

    @Override
    public void remove(int id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public void save(Category category, MultipartFile multipartFile) throws IOException {
        if (multipartFile != null && !multipartFile.isEmpty()) {
            String fileName = System.nanoTime() + "_" + multipartFile.getOriginalFilename();
            File file = new File(imageUploadPath + fileName);
            multipartFile.transferTo(file);
            category.setImage(fileName);
        }
        categoryRepository.save(category);
    }

    @Override
    public Category findById(Integer id) {
        Optional<Category> byId = categoryRepository.findById(id);
        return byId.get();
    }

    public Map<String, List<Category>> getParentCategoriesWithChildren() {
        List<Category> categories = categoryRepository.findAll();

        Map<String, List<Category>> parentCategoriesMap = new HashMap<>();

        for (Category category : categories) {
            String parentCategory = category.getParentCategory();
            if (!parentCategoriesMap.containsKey(parentCategory)) {
                parentCategoriesMap.put(parentCategory, new ArrayList<>());
            }
            parentCategoriesMap.get(parentCategory).add(category);
        }

        return parentCategoriesMap;
    }
}
