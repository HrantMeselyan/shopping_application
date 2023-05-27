package com.example.shopping_application.service;

import com.example.shopping_application.entity.Comments;

import java.util.List;

public interface CommentService {

    List<Comments> findAllCategory();
    void remove(int id);
    void save(Comments comment);

    List<Comments> findAllByProductId(int id);
}
