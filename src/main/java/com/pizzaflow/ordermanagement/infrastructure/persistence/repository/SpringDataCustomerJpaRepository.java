package com.pizzaflow.ordermanagement.infrastructure.persistence.repository;

import com.pizzaflow.ordermanagement.infrastructure.persistence.entity.CustomerJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataCustomerJpaRepository extends JpaRepository<CustomerJpaEntity, String> {
}
