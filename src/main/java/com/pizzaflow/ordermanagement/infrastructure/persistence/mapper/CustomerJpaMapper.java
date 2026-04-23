package com.pizzaflow.ordermanagement.infrastructure.persistence.mapper;

import com.pizzaflow.ordermanagement.domain.model.Customer;
import com.pizzaflow.ordermanagement.infrastructure.persistence.entity.CustomerJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class CustomerJpaMapper {

    public Customer toDomain(CustomerJpaEntity entity) {
        return new Customer(entity.getId(), entity.getName());
    }

    public CustomerJpaEntity toEntity(Customer customer) {
        return new CustomerJpaEntity(customer.getId(), customer.getName());
    }
}
