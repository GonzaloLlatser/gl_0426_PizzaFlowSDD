package com.pizzaflow.ordermanagement.application.usecase;

import com.pizzaflow.ordermanagement.application.port.out.CustomerRepository;
import com.pizzaflow.ordermanagement.application.port.out.OrderRepository;
import com.pizzaflow.ordermanagement.domain.model.Customer;
import com.pizzaflow.ordermanagement.domain.model.Order;

public class CreateOrderUseCase {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;

    public CreateOrderUseCase(OrderRepository orderRepository, CustomerRepository customerRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
    }

    public Order execute(String orderId, String customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found: " + customerId));

        Order order = new Order(orderId, customer);
        return orderRepository.save(order);
    }
}
