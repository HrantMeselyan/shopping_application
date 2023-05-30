package com.example.shopping_application.entity;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Created by Ashot Simonyan on 21.05.23.
 */

@Entity
@Data
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String name;

    private String image;
}
