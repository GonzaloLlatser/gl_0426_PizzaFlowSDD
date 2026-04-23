package com.pizzaflow.ordermanagement.infrastructure.persistence.repository;

import com.pizzaflow.ordermanagement.infrastructure.persistence.entity.OrderJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataOrderJpaRepository extends JpaRepository<OrderJpaEntity, String> {
}
