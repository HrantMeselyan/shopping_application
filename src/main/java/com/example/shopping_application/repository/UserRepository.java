package com.example.shopping_application.repository;

import com.example.shopping_application.entity.Gender;
import com.example.shopping_application.entity.User;
import com.example.shopping_application.entity.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByEmail(String email);

    @Query("update User u set u.name = :name, u.surname = :surname,u.email = :email," +
            "u.password = :password,u.address = :address,u.phoneNumber = :phoneNumber," +
            "u.postCode = :postCode,u.profilePic = :pic,u.userType = :type," +
            "u.gender = :gender where u.id = :id")
    void updateUserById(@Param("id") int id, @Param("name") String name, @Param("surname") String surname,
                        @Param("email") String email, @Param("password") String password, @Param("address") String address,
                        @Param("phoneNumber") String phoneNumber, @Param("postCode") String postCode, @Param("pic") String profilePic,
                        @Param("type") UserType userType, @Param("gender")Gender gender);
}
