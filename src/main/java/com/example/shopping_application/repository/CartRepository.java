package com.example.shopping_application.repository;

import com.example.shopping_application.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Integer> {
    List<Cart> findAllByUserId(int id);

    void deleteByUserIdAndProducts_Id(int userId, int productId);
}
