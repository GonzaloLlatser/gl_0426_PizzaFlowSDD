package com.pizzaflow.ordermanagement.domain.exception;

public class OnlyOrdersInPreparationCanBeMarkedReadyException extends BusinessException {

    public OnlyOrdersInPreparationCanBeMarkedReadyException() {
        super("Only orders in preparation can be marked as ready.");
    }
}
