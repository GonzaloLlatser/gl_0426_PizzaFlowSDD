package com.pizzaflow.ordermanagement.application.usecase;

import com.pizzaflow.ordermanagement.application.port.out.OrderRepository;
import com.pizzaflow.ordermanagement.domain.model.Order;

public class MarkOrderReadyUseCase {

    private final OrderRepository orderRepository;

    public MarkOrderReadyUseCase(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order execute(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + orderId));

        order.markReady();
        return orderRepository.save(order);
    }
}
