package com.example.shopping_application.impl;

import com.example.shopping_application.entity.Comments;
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
    public List<Comments> findAllCategory() {
        return commentsRepository.findAll();
    }

    @Override
    public void remove(int id) {
        commentsRepository.deleteById(id);
    }

    @Override
    public void save(Comments comment) {
        commentsRepository.save(comment);
    }
}
