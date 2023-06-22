package com.example.shopping_application.repository;

import com.example.shopping_application.entity.Delivery;
import com.example.shopping_application.entity.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeliveryRepository extends JpaRepository<Delivery, Integer> {

    Page<Delivery> findAllByUserIdAndOrderStatus(Integer id, Status status, Pageable pageable);
    Page<Delivery> findAllByOrderStatus(Status status,Pageable pageable);
}
