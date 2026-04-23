package com.pizzaflow.ordermanagement.domain.exception;

public class OrderMustBeConfirmedBeforePaymentException extends BusinessException {

    public OrderMustBeConfirmedBeforePaymentException() {
        super("Order must be confirmed before payment.");
    }
}
