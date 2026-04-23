package com.pizzaflow.ordermanagement.domain.exception;

public class CancelledOrderCannotTransitionException extends BusinessException {

    public CancelledOrderCannotTransitionException() {
        super("Cancelled orders cannot transition further.");
    }
}
