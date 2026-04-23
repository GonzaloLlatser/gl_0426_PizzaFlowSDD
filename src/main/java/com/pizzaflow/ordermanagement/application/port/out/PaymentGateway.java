package com.pizzaflow.ordermanagement.application.port.out;

public interface PaymentGateway {

    boolean isPaymentSuccessful(String paymentReference);
}
