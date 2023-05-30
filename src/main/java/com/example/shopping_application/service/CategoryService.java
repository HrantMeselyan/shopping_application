package com.example.shopping_application.service;

import com.example.shopping_application.entity.Category;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface CategoryService {

    List<Category> findAllCategory();

    void remove(int id);

    void save(Category category, MultipartFile multipartFile) throws IOException;

    Optional<Category> findById(Integer id);
}
