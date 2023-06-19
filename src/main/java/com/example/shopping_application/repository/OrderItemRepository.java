package com.example.shopping_application.repository;

import com.example.shopping_application.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
    void deleteByOrder_IdAndProduct_Id(int order, int productId);

}
