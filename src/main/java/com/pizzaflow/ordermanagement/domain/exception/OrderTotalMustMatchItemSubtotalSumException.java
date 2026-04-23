package com.pizzaflow.ordermanagement.domain.exception;

public class OrderTotalMustMatchItemSubtotalSumException extends BusinessException {

    public OrderTotalMustMatchItemSubtotalSumException() {
        super("Order total must match the sum of item subtotals.");
    }
}
