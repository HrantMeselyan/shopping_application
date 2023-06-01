package com.example.shopping_application.entity;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
@Table(name = "image")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String image;

}
