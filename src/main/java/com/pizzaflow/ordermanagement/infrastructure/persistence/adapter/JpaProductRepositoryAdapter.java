package com.pizzaflow.ordermanagement.infrastructure.persistence.adapter;

import com.pizzaflow.ordermanagement.application.port.out.ProductRepository;
import com.pizzaflow.ordermanagement.domain.model.Product;
import com.pizzaflow.ordermanagement.infrastructure.persistence.mapper.ProductJpaMapper;
import com.pizzaflow.ordermanagement.infrastructure.persistence.repository.SpringDataProductJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class JpaProductRepositoryAdapter implements ProductRepository {

    private final SpringDataProductJpaRepository repository;
    private final ProductJpaMapper mapper;

    public JpaProductRepositoryAdapter(SpringDataProductJpaRepository repository, ProductJpaMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Optional<Product> findById(String productId) {
        return repository.findById(productId).map(mapper::toDomain);
    }
}
