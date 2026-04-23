package com.pizzaflow.ordermanagement.domain.exception;

public class OrderItemQuantityMustBeGreaterThanZeroException extends BusinessException {

    public OrderItemQuantityMustBeGreaterThanZeroException() {
        super("Order item quantity must be greater than zero.");
    }
}
