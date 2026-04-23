package com.pizzaflow.ordermanagement.domain.exception;

public class OrderMustBePaidBeforePreparationException extends BusinessException {

    public OrderMustBePaidBeforePreparationException() {
        super("Order must be paid before preparation starts.");
    }
}
