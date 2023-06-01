package com.example.shopping_application.service;

import com.example.shopping_application.entity.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> findAllCategory();

    void remove(int id);

    void save(Comment comment);

    List<Comment> findAllByProductId(int id);
}
