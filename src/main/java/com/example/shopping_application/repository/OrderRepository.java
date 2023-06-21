package com.example.shopping_application.repository;

import com.example.shopping_application.entity.Order;
import com.example.shopping_application.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    @Query("SELECT o FROM Order o WHERE o.user.id = :userId ORDER BY o.dateTime DESC")
    List<Order> findLast15OrdersByUserId(@Param("userId") int userId, Pageable pageable);

    List<Order> findAllByUserId(Integer id);

    Optional<Order> findByUserIdAndStatus(int userId, Status status);

}
