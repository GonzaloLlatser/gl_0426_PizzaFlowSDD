package com.pizzaflow.ordermanagement.domain.exception;

public class OrderModificationNotAllowedAfterConfirmationException extends BusinessException {

    public OrderModificationNotAllowedAfterConfirmationException() {
        super("Order cannot be modified after confirmation.");
    }
}
