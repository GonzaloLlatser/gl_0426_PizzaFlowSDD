package com.pizzaflow.ordermanagement.infrastructure.persistence.adapter;

import com.pizzaflow.ordermanagement.application.port.out.OrderRepository;
import com.pizzaflow.ordermanagement.domain.model.Order;
import com.pizzaflow.ordermanagement.infrastructure.persistence.entity.OrderJpaEntity;
import com.pizzaflow.ordermanagement.infrastructure.persistence.mapper.OrderJpaMapper;
import com.pizzaflow.ordermanagement.infrastructure.persistence.repository.SpringDataOrderJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class JpaOrderRepositoryAdapter implements OrderRepository {

    private final SpringDataOrderJpaRepository repository;
    private final OrderJpaMapper mapper;

    public JpaOrderRepositoryAdapter(SpringDataOrderJpaRepository repository, OrderJpaMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Optional<Order> findById(String orderId) {
        return repository.findById(orderId).map(mapper::toDomain);
    }

    @Override
    public Order save(Order order) {
        OrderJpaEntity savedEntity = repository.save(mapper.toEntity(order));
        return mapper.toDomain(savedEntity);
    }
}
