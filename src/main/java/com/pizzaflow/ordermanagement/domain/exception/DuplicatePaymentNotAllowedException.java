package com.pizzaflow.ordermanagement.domain.exception;

public class DuplicatePaymentNotAllowedException extends BusinessException {

    public DuplicatePaymentNotAllowedException() {
        super("Duplicate payment requests are not allowed.");
    }
}
