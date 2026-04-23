package com.pizzaflow.ordermanagement.application.port.out;

import com.pizzaflow.ordermanagement.domain.model.Customer;

import java.util.Optional;

public interface CustomerRepository {

    Optional<Customer> findById(String customerId);
}
