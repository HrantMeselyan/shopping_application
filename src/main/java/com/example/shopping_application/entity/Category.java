package com.example.shopping_application.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

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

    @ManyToMany(mappedBy = "categories")
    private List<Product> products;
}
