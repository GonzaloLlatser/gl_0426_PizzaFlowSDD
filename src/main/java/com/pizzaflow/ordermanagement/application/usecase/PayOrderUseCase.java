package com.pizzaflow.ordermanagement.application.usecase;

import com.pizzaflow.ordermanagement.application.port.out.OrderRepository;
import com.pizzaflow.ordermanagement.application.port.out.PaymentGateway;
import com.pizzaflow.ordermanagement.domain.model.Order;

public class PayOrderUseCase {

    private final OrderRepository orderRepository;
    private final PaymentGateway paymentGateway;

    public PayOrderUseCase(OrderRepository orderRepository, PaymentGateway paymentGateway) {
        this.orderRepository = orderRepository;
        this.paymentGateway = paymentGateway;
    }

    public Order execute(String orderId, String paymentReference) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + orderId));

        boolean paymentSuccessful = paymentGateway.isPaymentSuccessful(paymentReference);
        if (!paymentSuccessful) {
            throw new IllegalStateException("Payment validation failed for reference: " + paymentReference);
        }

        order.pay(paymentReference);
        return orderRepository.save(order);
    }
}
