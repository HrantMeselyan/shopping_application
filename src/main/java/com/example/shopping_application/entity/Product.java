package com.example.shopping_application.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Ashot Simonyan on 21.05.23.
 */

@Entity
@Data
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private int productCode;
    private String description;
    @Column(nullable = false)
    private double price;
    @Column(nullable = false)
    private int count;

    @ManyToOne(optional = false)
    private User user;

    @ManyToMany(mappedBy = "products")
    private Set<Category> categories = new HashSet<>();
}
