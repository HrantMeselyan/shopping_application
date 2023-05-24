package com.example.shopping_application.entity;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Created by Ashot Simonyan on 21.05.23.
 */

@Entity
@Data
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String surname;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String address;
    private int phoneNumber;
    @Column(nullable = false)
    private String postCode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserType userType;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;
}
