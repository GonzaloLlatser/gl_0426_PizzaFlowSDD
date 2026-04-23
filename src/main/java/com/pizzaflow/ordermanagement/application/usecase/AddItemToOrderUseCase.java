package com.pizzaflow.ordermanagement.application.usecase;

import com.pizzaflow.ordermanagement.application.port.out.OrderRepository;
import com.pizzaflow.ordermanagement.application.port.out.ProductRepository;
import com.pizzaflow.ordermanagement.domain.model.Order;
import com.pizzaflow.ordermanagement.domain.model.Product;

public class AddItemToOrderUseCase {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public AddItemToOrderUseCase(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    public Order execute(String orderId, String productId, int quantity) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + orderId));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + productId));

        order.addItem(product, quantity);
        return orderRepository.save(order);
    }
}
