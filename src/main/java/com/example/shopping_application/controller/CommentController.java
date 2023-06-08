package com.example.shopping_application.controller;

import com.example.shopping_application.dto.commentDto.CommentRequestDto;
import com.example.shopping_application.mapper.UserMapper;
import com.example.shopping_application.security.CurrentUser;
import com.example.shopping_application.service.CommentService;
import com.example.shopping_application.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final ProductService productService;

    @GetMapping("product/{id}")
    public String singleProductPageComment(@PathVariable("id") int id,
                                           ModelMap modelMap,
                                           @AuthenticationPrincipal CurrentUser currentUser) {
        modelMap.addAttribute("product", productService.findById(id));
        modelMap.addAttribute("comments", commentService.findAllByProductId(id));
        modelMap.addAttribute("user", UserMapper.currentUserToUser(currentUser));
        return "singleProductPage";
    }

    @PostMapping("product/{id}")
    public String singleProductPageAddComment(@PathVariable("id") int id,
                                              @ModelAttribute CommentRequestDto commentRequestDto,
                                              @AuthenticationPrincipal CurrentUser currentUser) {
        commentService.save(commentRequestDto,currentUser.getUser());
        return "redirect:/comments/product/" + id;
    }

    @GetMapping("/remove")
    public String removeComment(@RequestParam("comm_id") int id, @RequestParam("product_id") int productId) {
        commentService.remove(id);
        return "redirect:/comments/product/" + productId;
    }
}
