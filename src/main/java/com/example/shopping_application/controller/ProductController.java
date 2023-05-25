package com.example.shopping_application.controller;

import com.example.shopping_application.entity.Category;
import com.example.shopping_application.entity.Product;
import com.example.shopping_application.service.CategoryService;
import com.example.shopping_application.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/{id}")
    public String productPage(ModelMap modelmap, @PathVariable("id") int id) {
        modelmap.addAttribute(productService.findByUserId(id));
        return "products";
    }

    @PostMapping("/add")
    public String addProduct(@ModelAttribute Product product) {
        productService.save(product);
        return "redirect:/";
    }

    @GetMapping("/remove")
    public String removeProduct(@RequestParam("id") int id) {
        productService.remove(id);
        return "redirect:/patients";
    }

}
