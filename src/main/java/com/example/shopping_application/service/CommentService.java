package com.example.shopping_application.service;

import com.example.shopping_application.dto.commentDto.CommentRequestDto;
import com.example.shopping_application.dto.commentDto.CommentResponseDto;
import com.example.shopping_application.entity.Product;
import com.example.shopping_application.entity.User;

import java.util.List;

public interface CommentService {

    List<CommentResponseDto> findAllCategory();

    void remove(int id);

    void save(CommentRequestDto commentRequestDto, User user, Product product);

    List<CommentResponseDto> findAllByProductId(int id);
}
