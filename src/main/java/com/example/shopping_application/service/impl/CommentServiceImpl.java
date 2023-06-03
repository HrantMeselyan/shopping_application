package com.example.shopping_application.service.impl;

import com.example.shopping_application.entity.Comment;
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
    public List<Comment> findAllCategory() {
        return commentsRepository.findAll();
    }

    @Override
    public void remove(int id) {
        commentsRepository.deleteById(id);
    }

    @Override
    public void save(Comment comment) {
        commentsRepository.save(comment);
    }

    @Override
    public List<Comment> findAllByProductId(int id) {
        return commentsRepository.findAllByProduct_Id(id);
    }
}
