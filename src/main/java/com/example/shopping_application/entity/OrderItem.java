package com.example.shopping_application.entity;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Created by Ashot Simonyan on 03.06.23.
 */

@Entity
@Data
@Table(name = "order_item")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int count;

    @ManyToOne
    private Product product;

    @ManyToOne
    private Order order;
}
