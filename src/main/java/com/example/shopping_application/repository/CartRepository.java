package com.example.shopping_application.repository;

import com.example.shopping_application.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Integer> {

    Optional<Cart> findAllByUser_Id(int userId);


    void deleteByUserId(int userId);
}
