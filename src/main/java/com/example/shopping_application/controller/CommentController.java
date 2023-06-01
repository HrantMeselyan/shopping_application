package com.example.shopping_application.controller;

import com.example.shopping_application.entity.Comment;
import com.example.shopping_application.entity.Product;
import com.example.shopping_application.security.CurrentUser;
import com.example.shopping_application.service.CommentService;
import com.example.shopping_application.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

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
        Product byId = productService.findById(id);
        if (byId != null) {
            modelMap.addAttribute("product", byId);
            modelMap.addAttribute("comments", commentService.findAllByProductId(id));
            modelMap.addAttribute("user", currentUser.getUser());
            return "singleProductPage";
        } else {
            return "redirect:/products/" + id;
        }
    }

    @PostMapping("product/{id}")
    public String singleProductPageAddComment(@PathVariable("id") int id,
                                              @RequestParam("comm") String comm,
                                              @AuthenticationPrincipal CurrentUser currentUser) {
        Comment comment = new Comment();
        comment.setComment(comm);
        Product byId = productService.findById(id);
        if (byId != null) {
            comment.setProduct(byId);
            comment.setUser(currentUser.getUser());
            comment.setCommentDateTime(new Date());
            commentService.save(comment);
            return "redirect:/comments/product/" + id;
        } else {
            return "singleProductPage";
        }

    }

    @GetMapping("/remove")
    public String removeComment(@RequestParam("comm_id") int id, @RequestParam("product_id") int productId) {
        commentService.remove(id);
        return "redirect:/comments/product/" + productId;
    }
}
