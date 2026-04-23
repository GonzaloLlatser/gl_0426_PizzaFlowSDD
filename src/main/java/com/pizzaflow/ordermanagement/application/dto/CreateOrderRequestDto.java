package com.pizzaflow.ordermanagement.application.dto;

public class CreateOrderRequestDto {

    private String orderId;
    private String customerId;

    public CreateOrderRequestDto() {
    }

    public CreateOrderRequestDto(String orderId, String customerId) {
        this.orderId = orderId;
        this.customerId = customerId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}
