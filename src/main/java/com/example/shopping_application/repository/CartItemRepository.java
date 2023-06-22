package com.example.shopping_application.repository;

import com.example.shopping_application.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    void deleteByCart_IdAndProduct_Id(int cartId, int productId);
    List<CartItem> findTop4ByCartIdOrderByIdDesc(int cartId);

}
