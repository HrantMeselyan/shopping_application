package com.example.shopping_application.repository;

import com.example.shopping_application.entity.Order;
import com.example.shopping_application.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
    void deleteByProduct_IdAndId(int product_id,int orderItem_id);

    List<OrderItem> findAllByOrder_Id(int id);
}
