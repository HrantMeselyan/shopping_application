package com.example.shopping_application.repository;

import com.example.shopping_application.entity.Order;
import com.example.shopping_application.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findAllByUserId(Integer id);

    Order findByUserIdAndStatus(int userId, Status status);

}
