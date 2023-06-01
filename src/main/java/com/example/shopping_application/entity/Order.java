package com.example.shopping_application.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * Created by Ashot Simonyan on 21.05.23.
 */

@Entity
@Data
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column(nullable = false)
    private Date orderDate;
    private int totalAmount;
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne(optional = false)
    private User user;

    @ManyToMany
    @JoinTable(name = "orders_product", uniqueConstraints = @UniqueConstraint(columnNames = {"orders_id", "product_id"}),
            joinColumns = @JoinColumn(name = "orders_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products;
}
