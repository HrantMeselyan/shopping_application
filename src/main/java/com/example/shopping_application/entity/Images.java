package com.example.shopping_application.entity;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
@Table(name = "images")
public class Images {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String image;

    @ManyToOne(optional = false)
    private Product product;
}
