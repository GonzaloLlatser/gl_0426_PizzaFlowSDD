package com.pizzaflow.ordermanagement.application.port.out;

import com.pizzaflow.ordermanagement.domain.model.Order;

import java.util.Optional;

public interface OrderRepository {

    Optional<Order> findById(String orderId);

    Order save(Order order);
}
