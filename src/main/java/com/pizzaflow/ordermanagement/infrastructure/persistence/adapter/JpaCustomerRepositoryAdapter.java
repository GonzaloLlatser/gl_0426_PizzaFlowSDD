package com.pizzaflow.ordermanagement.infrastructure.persistence.adapter;

import com.pizzaflow.ordermanagement.application.port.out.CustomerRepository;
import com.pizzaflow.ordermanagement.domain.model.Customer;
import com.pizzaflow.ordermanagement.infrastructure.persistence.mapper.CustomerJpaMapper;
import com.pizzaflow.ordermanagement.infrastructure.persistence.repository.SpringDataCustomerJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class JpaCustomerRepositoryAdapter implements CustomerRepository {

    private final SpringDataCustomerJpaRepository repository;
    private final CustomerJpaMapper mapper;

    public JpaCustomerRepositoryAdapter(SpringDataCustomerJpaRepository repository, CustomerJpaMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Optional<Customer> findById(String customerId) {
        return repository.findById(customerId).map(mapper::toDomain);
    }
}
