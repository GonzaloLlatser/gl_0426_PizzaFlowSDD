package com.pizzaflow.ordermanagement.domain.model;

import java.util.Objects;

public class Customer {

    private final String id;
    private final String name;

    public Customer(String id, String name) {
        this.id = requireNonBlank(id, "Customer id must not be blank.");
        this.name = requireNonBlank(name, "Customer name must not be blank.");
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    private static String requireNonBlank(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(message);
        }
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Customer customer)) {
            return false;
        }
        return Objects.equals(id, customer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
