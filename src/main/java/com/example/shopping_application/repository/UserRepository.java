package com.example.shopping_application.repository;

import com.example.shopping_application.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.profilePic = :picName WHERE u.id = :userId")
    void updatePicName(@Param("userId") Integer userId, @Param("picName") String picName);
}
