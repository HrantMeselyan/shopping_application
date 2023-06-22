package com.example.shopping_application.repository;

import com.example.shopping_application.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentsRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByProductIdOrderByIdDesc(int productId);

    List<Comment> findAllByProduct_Id(int Id);
}
