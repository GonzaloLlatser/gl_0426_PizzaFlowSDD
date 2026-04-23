package com.pizzaflow.ordermanagement.infrastructure.config;

import com.pizzaflow.ordermanagement.infrastructure.persistence.entity.CustomerJpaEntity;
import com.pizzaflow.ordermanagement.infrastructure.persistence.entity.ProductJpaEntity;
import com.pizzaflow.ordermanagement.infrastructure.persistence.repository.SpringDataCustomerJpaRepository;
import com.pizzaflow.ordermanagement.infrastructure.persistence.repository.SpringDataProductJpaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class DataLoaderConfig {

    @Bean
    public CommandLineRunner loadInitialData(
            SpringDataCustomerJpaRepository customerRepository,
            SpringDataProductJpaRepository productRepository
    ) {
        return args -> {
            if (customerRepository.count() == 0) {
                customerRepository.save(new CustomerJpaEntity("customer-001", "John Doe"));
            }

            if (productRepository.count() == 0) {
                productRepository.save(new ProductJpaEntity("pizza-margarita", "Pizza Margarita", new BigDecimal("8.50"), true));
                productRepository.save(new ProductJpaEntity("pizza-pepperoni", "Pizza Pepperoni", new BigDecimal("9.95"), true));
                productRepository.save(new ProductJpaEntity("pizza-vegetarian", "Pizza Vegetarian", new BigDecimal("9.25"), true));
                productRepository.save(new ProductJpaEntity("drink-coca-cola", "Coca-Cola", new BigDecimal("2.50"), true));
                productRepository.save(new ProductJpaEntity("drink-water", "Water", new BigDecimal("1.80"), true));
            }
        };
    }
}
