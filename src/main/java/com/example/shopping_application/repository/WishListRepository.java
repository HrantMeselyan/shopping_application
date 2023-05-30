package com.example.shopping_application.repository;

import com.example.shopping_application.entity.WishList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishListRepository extends JpaRepository<WishList, Integer> {
}
