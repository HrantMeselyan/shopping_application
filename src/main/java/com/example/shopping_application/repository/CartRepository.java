package com.example.shopping_application.repository;

import com.example.shopping_application.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Integer> {
    List<Cart> findAllByUserId(int id);

    void deleteByUserIdAndCartItems_Product_Id(int userId, int productId);

    Optional<Cart> findAllByUser_Id(int userId);


    void deleteByUserId(int userId);
}
