package com.pizzaflow.ordermanagement.infrastructure.persistence.mapper;

import com.pizzaflow.ordermanagement.domain.model.Customer;
import com.pizzaflow.ordermanagement.domain.model.Order;
import com.pizzaflow.ordermanagement.domain.model.OrderItem;
import com.pizzaflow.ordermanagement.domain.model.OrderStatus;
import com.pizzaflow.ordermanagement.domain.model.Product;
import com.pizzaflow.ordermanagement.infrastructure.persistence.entity.OrderItemJpaEntity;
import com.pizzaflow.ordermanagement.infrastructure.persistence.entity.OrderJpaEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderJpaMapper {

    public Order toDomain(OrderJpaEntity entity) {
        Customer customer = new Customer(entity.getCustomerId(), entity.getCustomerName());
        List<OrderItem> items = new ArrayList<>();

        for (OrderItemJpaEntity itemEntity : entity.getItems()) {
            Product productSnapshot = new Product(
                    itemEntity.getProductId(),
                    itemEntity.getProductName(),
                    itemEntity.getUnitPrice(),
                    true
            );

            items.add(OrderItem.reconstitute(
                    productSnapshot,
                    itemEntity.getQuantity(),
                    itemEntity.getUnitPrice(),
                    itemEntity.getSubtotal()
            ));
        }

        return Order.reconstitute(
                entity.getId(),
                customer,
                items,
                entity.getTotal(),
                OrderStatus.valueOf(entity.getStatus()),
                entity.getPaymentReference()
        );
    }

    public OrderJpaEntity toEntity(Order order) {
        OrderJpaEntity entity = new OrderJpaEntity(
                order.getId(),
                order.getCustomer().getId(),
                order.getCustomer().getName(),
                order.getStatus().name(),
                order.getTotal(),
                order.getPaymentReference()
        );

        List<OrderItemJpaEntity> itemEntities = new ArrayList<>();
        for (OrderItem item : order.getItems()) {
            itemEntities.add(new OrderItemJpaEntity(
                    item.getProduct().getId(),
                    item.getProduct().getName(),
                    item.getQuantity(),
                    item.getUnitPrice(),
                    item.getSubtotal()
            ));
        }

        entity.replaceItems(itemEntities);
        return entity;
    }
}
