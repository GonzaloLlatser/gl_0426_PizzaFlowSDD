package com.pizzaflow.ordermanagement.infrastructure.config;

import com.pizzaflow.ordermanagement.application.port.out.CustomerRepository;
import com.pizzaflow.ordermanagement.application.port.out.OrderRepository;
import com.pizzaflow.ordermanagement.application.port.out.PaymentGateway;
import com.pizzaflow.ordermanagement.application.port.out.ProductRepository;
import com.pizzaflow.ordermanagement.application.usecase.AddItemToOrderUseCase;
import com.pizzaflow.ordermanagement.application.usecase.CancelOrderUseCase;
import com.pizzaflow.ordermanagement.application.usecase.ConfirmOrderUseCase;
import com.pizzaflow.ordermanagement.application.usecase.CreateOrderUseCase;
import com.pizzaflow.ordermanagement.application.usecase.DeliverOrderUseCase;
import com.pizzaflow.ordermanagement.application.usecase.GetOrderByIdUseCase;
import com.pizzaflow.ordermanagement.application.usecase.MarkOrderReadyUseCase;
import com.pizzaflow.ordermanagement.application.usecase.PayOrderUseCase;
import com.pizzaflow.ordermanagement.application.usecase.StartPreparationUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationUseCaseConfig {

    @Bean
    public CreateOrderUseCase createOrderUseCase(OrderRepository orderRepository, CustomerRepository customerRepository) {
        return new CreateOrderUseCase(orderRepository, customerRepository);
    }

    @Bean
    public AddItemToOrderUseCase addItemToOrderUseCase(OrderRepository orderRepository, ProductRepository productRepository) {
        return new AddItemToOrderUseCase(orderRepository, productRepository);
    }

    @Bean
    public ConfirmOrderUseCase confirmOrderUseCase(OrderRepository orderRepository) {
        return new ConfirmOrderUseCase(orderRepository);
    }

    @Bean
    public PayOrderUseCase payOrderUseCase(OrderRepository orderRepository, PaymentGateway paymentGateway) {
        return new PayOrderUseCase(orderRepository, paymentGateway);
    }

    @Bean
    public StartPreparationUseCase startPreparationUseCase(OrderRepository orderRepository) {
        return new StartPreparationUseCase(orderRepository);
    }

    @Bean
    public MarkOrderReadyUseCase markOrderReadyUseCase(OrderRepository orderRepository) {
        return new MarkOrderReadyUseCase(orderRepository);
    }

    @Bean
    public DeliverOrderUseCase deliverOrderUseCase(OrderRepository orderRepository) {
        return new DeliverOrderUseCase(orderRepository);
    }

    @Bean
    public CancelOrderUseCase cancelOrderUseCase(OrderRepository orderRepository) {
        return new CancelOrderUseCase(orderRepository);
    }

    @Bean
    public GetOrderByIdUseCase getOrderByIdUseCase(OrderRepository orderRepository) {
        return new GetOrderByIdUseCase(orderRepository);
    }
}
