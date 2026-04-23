package com.pizzaflow.ordermanagement.domain.exception;

public class ProductMustBeAvailableException extends BusinessException {

    public ProductMustBeAvailableException() {
        super("Product must be available to be added to the order.");
    }
}
