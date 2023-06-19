package com.example.shopping_application.repository;

import com.example.shopping_application.entity.Order;
import com.example.shopping_application.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findAllByUserId(Integer id);

    Optional<Order> findByUserIdAndStatus(int userId, Status status);

}
