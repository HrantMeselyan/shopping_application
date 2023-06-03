package com.example.shopping_application.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

/**
 * Created by Ashot Simonyan on 03.06.23.
 */

@Entity
@Data
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    private User user;

    @OneToMany
    private List<Product> products;

}
