package com.example.shopping_application.service;

import com.example.shopping_application.dto.commentDto.CommentRequestDto;
import com.example.shopping_application.dto.commentDto.CommentResponseDto;
import com.example.shopping_application.entity.Comment;
import com.example.shopping_application.entity.User;

import java.util.List;

public interface CommentService {

    List<CommentResponseDto> findAllCategory();

    void remove(int id);

    void save(CommentRequestDto commentRequestDto, User user,int productId);

    List<CommentResponseDto> findAllByProductId(int id);

    List<Comment> findAllByLimit(int productId);
}
