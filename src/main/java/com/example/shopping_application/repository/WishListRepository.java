package com.example.shopping_application.repository;

import com.example.shopping_application.entity.Product;
import com.example.shopping_application.entity.WishList;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WishListRepository extends JpaRepository<WishList, Integer> {

    List<WishList> findAllByUser_Id(Integer id);

    Optional<WishList> findByProduct(Product product);

    void deleteAllByProduct_Id(Integer id);
}
