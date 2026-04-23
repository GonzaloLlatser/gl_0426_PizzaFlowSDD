package com.pizzaflow.ordermanagement.domain.model;

import com.pizzaflow.ordermanagement.domain.exception.OrderTotalMustMatchItemSubtotalSumException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Order {

    private final String id;
    private final Customer customer;
    private final List<OrderItem> items;
    private BigDecimal total;
    private OrderStatus status;

    public Order(String id, Customer customer) {
        this.id = requireNonBlank(id, "Order id must not be blank.");
        this.customer = requireCustomer(customer);
        this.items = new ArrayList<>();
        this.total = BigDecimal.ZERO;
        this.status = OrderStatus.CREATED;
    }

    public String getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<OrderItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public BigDecimal getTotal() {
        return total;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public boolean hasItems() {
        return !items.isEmpty();
    }

    public void addItem(Product product, int quantity) {
        OrderItem existingItem = findItemByProductId(product.getId());
        if (existingItem == null) {
            items.add(new OrderItem(product, quantity));
        } else {
            existingItem.increaseQuantity(quantity);
        }
        recalculateTotal();
    }

    public void recalculateTotal() {
        BigDecimal calculatedTotal = BigDecimal.ZERO;
        for (OrderItem item : items) {
            calculatedTotal = calculatedTotal.add(item.getSubtotal());
        }

        if (calculatedTotal.signum() < 0) {
            throw new OrderTotalMustMatchItemSubtotalSumException();
        }

        this.total = calculatedTotal;
    }

    public void updateStatus(OrderStatus status) {
        this.status = Objects.requireNonNull(status, "Order status must not be null.");
    }

    private OrderItem findItemByProductId(String productId) {
        for (OrderItem item : items) {
            if (item.getProduct().getId().equals(productId)) {
                return item;
            }
        }
        return null;
    }

    private static String requireNonBlank(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(message);
        }
        return value;
    }

    private static Customer requireCustomer(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer must not be null.");
        }
        return customer;
    }
}
