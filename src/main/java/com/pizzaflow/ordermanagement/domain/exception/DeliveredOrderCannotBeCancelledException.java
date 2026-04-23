package com.pizzaflow.ordermanagement.domain.exception;

public class DeliveredOrderCannotBeCancelledException extends BusinessException {

    public DeliveredOrderCannotBeCancelledException() {
        super("Delivered orders cannot be cancelled.");
    }
}
