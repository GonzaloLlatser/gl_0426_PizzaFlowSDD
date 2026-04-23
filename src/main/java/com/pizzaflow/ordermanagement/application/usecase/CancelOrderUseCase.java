package com.pizzaflow.ordermanagement.application.usecase;

import com.pizzaflow.ordermanagement.application.port.out.OrderRepository;
import com.pizzaflow.ordermanagement.domain.model.Order;

public class CancelOrderUseCase {

    private final OrderRepository orderRepository;

    public CancelOrderUseCase(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order execute(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + orderId));

        order.cancel();
        return orderRepository.save(order);
    }
}
