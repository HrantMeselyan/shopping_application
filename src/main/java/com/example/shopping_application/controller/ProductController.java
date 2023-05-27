package com.example.shopping_application.controller;

import com.example.shopping_application.entity.Product;
import com.example.shopping_application.security.CurrentUser;
import com.example.shopping_application.service.CategoryService;
import com.example.shopping_application.service.CommentService;
import com.example.shopping_application.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final CommentService commentService;

    @GetMapping
    public String productPage(ModelMap modelMap) {
        modelMap.addAttribute("prod", productService.findAllProducts());
        return "products";
    }

    @GetMapping("{id}")
    public String currentProductPage(ModelMap modelmap, @PathVariable("id") int id) {
        modelmap.addAttribute("product",productService.findById(id));
        modelmap.addAttribute("comm",commentService.findAllByProductId(id));
        return "singleProductPage";
    }

    @GetMapping("/add")
    public String addProductPage(ModelMap modelMap) {
        modelMap.addAttribute("categories", categoryService.findAllCategory());
        return "addProducts";
    }

    @PostMapping("/add")
    public String addProduct(@ModelAttribute Product product,
                             @AuthenticationPrincipal CurrentUser currentUser,
                             @RequestParam("profile_pic") MultipartFile multipartFile) throws IOException {
        productService.save(product, multipartFile, currentUser);
        return "redirect:/";
    }

    @GetMapping("/remove")
    public String removeProduct(@RequestParam("id") int id) {
        productService.remove(id);
        return "redirect:/patients";
    }

}
