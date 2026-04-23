package com.pizzaflow.ordermanagement.application.usecase;

import com.pizzaflow.ordermanagement.application.port.out.OrderRepository;
import com.pizzaflow.ordermanagement.domain.model.Order;

public class DeliverOrderUseCase {

    private final OrderRepository orderRepository;

    public DeliverOrderUseCase(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order execute(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + orderId));

        order.deliver();
        return orderRepository.save(order);
    }
}
