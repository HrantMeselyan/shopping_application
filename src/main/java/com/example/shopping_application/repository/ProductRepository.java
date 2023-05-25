package com.example.shopping_application.repository;

import com.example.shopping_application.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Integer> {

    Optional<Product> findAllByUser_Id(int userId);
}
