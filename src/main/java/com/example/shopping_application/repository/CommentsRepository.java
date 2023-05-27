package com.example.shopping_application.repository;

import com.example.shopping_application.entity.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentsRepository extends JpaRepository<Comments,Integer> {

    List<Comments> findAllByProduct_Id(int Id);
}
