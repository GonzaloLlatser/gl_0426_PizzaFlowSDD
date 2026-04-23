package com.pizzaflow.ordermanagement.application.dto;

public class PayOrderRequestDto {

    private String paymentReference;

    public PayOrderRequestDto() {
    }

    public PayOrderRequestDto(String paymentReference) {
        this.paymentReference = paymentReference;
    }

    public String getPaymentReference() {
        return paymentReference;
    }

    public void setPaymentReference(String paymentReference) {
        this.paymentReference = paymentReference;
    }
}
