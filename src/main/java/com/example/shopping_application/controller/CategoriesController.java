package com.example.shopping_application.controller;

import com.example.shopping_application.entity.Category;
import com.example.shopping_application.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;


@Controller
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoriesController {
    private final CategoryService categoryService;
    @GetMapping("/add")
    public String addCategoryPage()  {
        return "addCategory";
    }
    @PostMapping("/add")
    public String addCategory(@ModelAttribute Category category, @RequestParam("pic") MultipartFile multipartFile) throws IOException {
        categoryService.save(category, multipartFile);
        return "redirect:/";
    }

    @GetMapping("/remove")
    public String removeCategory(@RequestParam("id") int id, @RequestParam("categoryId") int categoryId) {
        categoryService.remove(id);
        return "redirect:/category/" + categoryId;
    }

}
