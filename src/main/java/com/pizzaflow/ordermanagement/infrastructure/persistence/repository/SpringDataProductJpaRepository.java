package com.pizzaflow.ordermanagement.infrastructure.persistence.repository;

import com.pizzaflow.ordermanagement.infrastructure.persistence.entity.ProductJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataProductJpaRepository extends JpaRepository<ProductJpaEntity, String> {
}
