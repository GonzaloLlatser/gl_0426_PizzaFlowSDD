package com.pizzaflow.ordermanagement.infrastructure.persistence.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class OrderJpaEntity {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private String id;

    @Column(name = "customer_id", nullable = false)
    private String customerId;

    @Column(name = "customer_name", nullable = false)
    private String customerName;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "total", nullable = false, precision = 12, scale = 2)
    private BigDecimal total;

    @Column(name = "payment_reference")
    private String paymentReference;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderItemJpaEntity> items = new ArrayList<>();

    protected OrderJpaEntity() {
    }

    public OrderJpaEntity(
            String id,
            String customerId,
            String customerName,
            String status,
            BigDecimal total,
            String paymentReference
    ) {
        this.id = id;
        this.customerId = customerId;
        this.customerName = customerName;
        this.status = status;
        this.total = total;
        this.paymentReference = paymentReference;
    }

    public String getId() {
        return id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getStatus() {
        return status;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public String getPaymentReference() {
        return paymentReference;
    }

    public List<OrderItemJpaEntity> getItems() {
        return items;
    }

    public void replaceItems(List<OrderItemJpaEntity> newItems) {
        this.items.clear();
        for (OrderItemJpaEntity item : newItems) {
            item.setOrder(this);
            this.items.add(item);
        }
    }
}
