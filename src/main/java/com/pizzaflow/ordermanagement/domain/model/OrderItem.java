package com.pizzaflow.ordermanagement.domain.model;

import com.pizzaflow.ordermanagement.domain.exception.OrderItemQuantityMustBeGreaterThanZeroException;
import com.pizzaflow.ordermanagement.domain.exception.ProductMustBeAvailableException;
import com.pizzaflow.ordermanagement.domain.exception.OrderTotalMustMatchItemSubtotalSumException;

import java.math.BigDecimal;
import java.util.Objects;

public class OrderItem {

    private final Product product;
    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal subtotal;

    public OrderItem(Product product, int quantity) {
        this.product = requireProduct(product);
        validateProductAvailability(product);
        validateQuantity(quantity);
        this.quantity = quantity;
        this.unitPrice = product.getPrice();
        this.subtotal = calculateSubtotal(unitPrice, quantity);
    }

    public static OrderItem reconstitute(Product product, int quantity, BigDecimal unitPrice, BigDecimal subtotal) {
        Product requiredProduct = requireProduct(product);
        validateQuantity(quantity);
        BigDecimal requiredUnitPrice = requireNonNegative(unitPrice, "Order item unit price must not be null or negative.");
        BigDecimal requiredSubtotal = requireNonNegative(subtotal, "Order item subtotal must not be null or negative.");

        OrderItem orderItem = new OrderItem(requiredProduct, quantity);
        orderItem.unitPrice = requiredUnitPrice;
        orderItem.subtotal = requiredSubtotal;
        return orderItem;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void increaseQuantity(int quantityToAdd) {
        validateQuantity(quantityToAdd);
        this.quantity += quantityToAdd;
        recalculateSubtotal();
    }

    public void updateQuantity(int newQuantity) {
        validateQuantity(newQuantity);
        this.quantity = newQuantity;
        recalculateSubtotal();
    }

    private void recalculateSubtotal() {
        this.subtotal = calculateSubtotal(unitPrice, quantity);
    }

    private BigDecimal calculateSubtotal(BigDecimal unitPrice, int quantity) {
        BigDecimal calculatedSubtotal = unitPrice.multiply(BigDecimal.valueOf(quantity));
        if (calculatedSubtotal.signum() < 0) {
            throw new OrderTotalMustMatchItemSubtotalSumException();
        }
        return calculatedSubtotal;
    }

    private static Product requireProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product must not be null.");
        }
        return product;
    }

    private void validateProductAvailability(Product product) {
        if (!product.isAvailable()) {
            throw new ProductMustBeAvailableException();
        }
    }

    private static void validateQuantity(int quantity) {
        if (quantity <= 0) {
            throw new OrderItemQuantityMustBeGreaterThanZeroException();
        }
    }

    private static BigDecimal requireNonNegative(BigDecimal value, String message) {
        if (value == null || value.signum() < 0) {
            throw new IllegalArgumentException(message);
        }
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderItem orderItem)) {
            return false;
        }
        return Objects.equals(product.getId(), orderItem.product.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(product.getId());
    }
}
