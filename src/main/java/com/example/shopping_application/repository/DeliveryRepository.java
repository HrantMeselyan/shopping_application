package com.example.shopping_application.repository;

import com.example.shopping_application.entity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<Delivery,Integer> {
}
