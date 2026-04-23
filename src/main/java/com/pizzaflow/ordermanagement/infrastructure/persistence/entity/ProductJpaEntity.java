package com.pizzaflow.ordermanagement.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
public class ProductJpaEntity {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false, precision = 12, scale = 2)
    private BigDecimal price;

    @Column(name = "available", nullable = false)
    private boolean available;

    protected ProductJpaEntity() {
    }

    public ProductJpaEntity(String id, String name, BigDecimal price, boolean available) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.available = available;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public boolean isAvailable() {
        return available;
    }
}
