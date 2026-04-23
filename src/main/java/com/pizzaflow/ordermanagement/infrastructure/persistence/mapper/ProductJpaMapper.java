package com.pizzaflow.ordermanagement.infrastructure.persistence.mapper;

import com.pizzaflow.ordermanagement.domain.model.Product;
import com.pizzaflow.ordermanagement.infrastructure.persistence.entity.ProductJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductJpaMapper {

    public Product toDomain(ProductJpaEntity entity) {
        return new Product(entity.getId(), entity.getName(), entity.getPrice(), entity.isAvailable());
    }

    public ProductJpaEntity toEntity(Product product) {
        return new ProductJpaEntity(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.isAvailable()
        );
    }
}
