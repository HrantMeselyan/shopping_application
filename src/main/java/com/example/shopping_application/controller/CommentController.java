package com.example.shopping_application.controller;

import com.example.shopping_application.entity.Comments;
import com.example.shopping_application.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/add")
    public String addComment(@ModelAttribute Comments comment) {
        commentService.save(comment);
        return "redirect:/products/" + comment.getProduct().getId();
    }

    @GetMapping("/remove")
    public String removeComment(@RequestParam("id") int id, @RequestParam("commentId") int commentId) {
        commentService.remove(id);
        return "redirect:/items/" + commentId;
    }
}
