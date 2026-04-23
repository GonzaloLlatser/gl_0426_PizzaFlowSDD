package com.pizzaflow.ordermanagement.infrastructure.web;

import com.pizzaflow.ordermanagement.application.dto.OrderItemResponseDto;
import com.pizzaflow.ordermanagement.application.dto.OrderResponseDto;
import com.pizzaflow.ordermanagement.domain.model.Order;
import com.pizzaflow.ordermanagement.domain.model.OrderItem;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderResponseMapper {

    public OrderResponseDto toDto(Order order) {
        List<OrderItemResponseDto> itemDtos = new ArrayList<>();
        for (OrderItem item : order.getItems()) {
            itemDtos.add(new OrderItemResponseDto(
                    item.getProduct().getId(),
                    item.getProduct().getName(),
                    item.getQuantity(),
                    item.getUnitPrice(),
                    item.getSubtotal()
            ));
        }

        return new OrderResponseDto(
                order.getId(),
                order.getCustomer().getId(),
                order.getCustomer().getName(),
                order.getStatus().name(),
                order.getTotal(),
                order.getPaymentReference(),
                itemDtos
        );
    }
}
