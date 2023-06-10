package com.example.shopping_application.service.impl;

import com.example.shopping_application.dto.commentDto.CommentRequestDto;
import com.example.shopping_application.dto.commentDto.CommentResponseDto;
import com.example.shopping_application.entity.Comment;
import com.example.shopping_application.entity.Product;
import com.example.shopping_application.entity.User;
import com.example.shopping_application.mapper.CommentMapper;
import com.example.shopping_application.repository.CommentsRepository;
import com.example.shopping_application.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Ashot Simonyan on 21.05.23.
 */
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentsRepository commentsRepository;


    @Override
    public List<CommentResponseDto> findAllCategory() {
        return CommentMapper.commentRequestDtoToComment(commentsRepository.findAll());
    }

    @Override
    public void remove(int id) {
        commentsRepository.deleteById(id);
    }

    @Override
    public void save(CommentRequestDto commentRequestDto, User user, Product product) {
        Comment comment = CommentMapper.commentRequestDtoToComment(commentRequestDto);
        comment.setUser(user);
        comment.setProduct(product);
        commentsRepository.save(comment);
    }

    @Override
    public List<CommentResponseDto> findAllByProductId(int id) {
        return CommentMapper.commentRequestDtoToComment(commentsRepository.findAllByProduct_Id(id));
    }
}
