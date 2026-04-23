package com.pizzaflow.ordermanagement.application.port.out;

import com.pizzaflow.ordermanagement.domain.model.Product;

import java.util.Optional;

public interface ProductRepository {

    Optional<Product> findById(String productId);
}
