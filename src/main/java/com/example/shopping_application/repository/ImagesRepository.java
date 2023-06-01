package com.example.shopping_application.repository;

import com.example.shopping_application.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImagesRepository extends JpaRepository<Image, Integer> {
}
