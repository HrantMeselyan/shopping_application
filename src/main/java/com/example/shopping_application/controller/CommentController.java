package com.example.shopping_application.controller;

import com.example.shopping_application.dto.commentDto.CommentRequestDto;
import com.example.shopping_application.security.CurrentUser;
import com.example.shopping_application.service.CommentService;
import com.example.shopping_application.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final ProductService productService;

    @PostMapping("/add/{id}")
    public String addComment(@PathVariable("id") int id,
                                              @ModelAttribute CommentRequestDto commentRequestDto,
                                              @AuthenticationPrincipal CurrentUser currentUser) {
        commentService.save(commentRequestDto,currentUser.getUser(),productService.findBy_Id(id));
        return "redirect:/products/" + id;
    }

    @GetMapping("/remove")
    public String removeComment(@RequestParam("comment_id") int id, @RequestParam("product_id") int productId) {
        commentService.remove(id);
        return "redirect:/products/" + productId;
    }
}
