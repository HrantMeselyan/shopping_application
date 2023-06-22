package com.example.shopping_application.service.impl;

import com.example.shopping_application.dto.commentDto.CommentRequestDto;
import com.example.shopping_application.dto.commentDto.CommentResponseDto;
import com.example.shopping_application.dto.productDto.ProductDto;
import com.example.shopping_application.entity.Comment;
import com.example.shopping_application.entity.Product;
import com.example.shopping_application.entity.User;
import com.example.shopping_application.mapper.CommentMapper;
import com.example.shopping_application.repository.CommentsRepository;
import com.example.shopping_application.repository.ProductRepository;
import com.example.shopping_application.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by Ashot Simonyan on 21.05.23.
 */
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentsRepository commentsRepository;
    private final ProductRepository productRepository;


    @Override
    public List<CommentResponseDto> findAllCategory() {
        return CommentMapper.map(commentsRepository.findAll());
    }

    @Override
    public void remove(int id) {
        commentsRepository.deleteById(id);
    }

    @Override
    public void save(CommentRequestDto commentRequestDto, User user, int productId) {
        if (commentRequestDto.getComment() != null && !commentRequestDto.getComment().equals("")) {
            Optional<Product> byId = productRepository.findById(productId);
            if (byId.isPresent()) {
                Product product = byId.get();
                Comment comment = CommentMapper.map(commentRequestDto, user);
                comment.setProduct(product);
                commentsRepository.save(comment);
            }
        }
    }

    @Override
    public List<CommentResponseDto> findAllByProductId(int id) {
        return CommentMapper.map(commentsRepository.findAllByProduct_Id(id));
    }

    @Override
    public List<Comment> findAllByLimit(int productId) {
        return commentsRepository.findAllByProduct_Id(productId);
    }
}
