package com.example.orders.core.domain.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "price_of_unit")
    private Double priceOfUnit;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

}
