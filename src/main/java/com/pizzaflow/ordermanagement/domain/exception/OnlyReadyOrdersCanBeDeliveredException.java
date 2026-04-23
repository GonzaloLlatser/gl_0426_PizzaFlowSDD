package com.pizzaflow.ordermanagement.domain.exception;

public class OnlyReadyOrdersCanBeDeliveredException extends BusinessException {

    public OnlyReadyOrdersCanBeDeliveredException() {
        super("Only ready orders can be delivered.");
    }
}
