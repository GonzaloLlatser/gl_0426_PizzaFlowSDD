package com.pizzaflow.ordermanagement.domain.exception;

public class OrderCannotBeConfirmedWhenEmptyException extends BusinessException {

    public OrderCannotBeConfirmedWhenEmptyException() {
        super("Order cannot be confirmed when it has no items.");
    }
}
