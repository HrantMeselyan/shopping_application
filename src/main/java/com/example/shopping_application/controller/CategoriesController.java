package com.example.shopping_application.controller;

import com.example.shopping_application.entity.Category;
import com.example.shopping_application.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoriesController {
    private final CategoryService categoryService;

    @PostMapping("/add")
    public String addCategory(@ModelAttribute Category category){
        categoryService.save(category);
        return "redirect:/";
    }
    @GetMapping("/remove")
    public String removeCategory(@RequestParam("id") int id, @RequestParam("categoryId") int categoryId) {
        categoryService.remove(id);
        return "redirect:/category/" + categoryId;
    }

}
