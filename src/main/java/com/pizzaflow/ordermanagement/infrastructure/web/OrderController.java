package com.pizzaflow.ordermanagement.infrastructure.web;

import com.pizzaflow.ordermanagement.application.dto.AddItemToOrderRequestDto;
import com.pizzaflow.ordermanagement.application.dto.CreateOrderRequestDto;
import com.pizzaflow.ordermanagement.application.dto.OrderResponseDto;
import com.pizzaflow.ordermanagement.application.dto.PayOrderRequestDto;
import com.pizzaflow.ordermanagement.application.usecase.AddItemToOrderUseCase;
import com.pizzaflow.ordermanagement.application.usecase.CancelOrderUseCase;
import com.pizzaflow.ordermanagement.application.usecase.ConfirmOrderUseCase;
import com.pizzaflow.ordermanagement.application.usecase.CreateOrderUseCase;
import com.pizzaflow.ordermanagement.application.usecase.DeliverOrderUseCase;
import com.pizzaflow.ordermanagement.application.usecase.GetOrderByIdUseCase;
import com.pizzaflow.ordermanagement.application.usecase.MarkOrderReadyUseCase;
import com.pizzaflow.ordermanagement.application.usecase.PayOrderUseCase;
import com.pizzaflow.ordermanagement.application.usecase.StartPreparationUseCase;
import com.pizzaflow.ordermanagement.domain.model.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final CreateOrderUseCase createOrderUseCase;
    private final AddItemToOrderUseCase addItemToOrderUseCase;
    private final ConfirmOrderUseCase confirmOrderUseCase;
    private final PayOrderUseCase payOrderUseCase;
    private final StartPreparationUseCase startPreparationUseCase;
    private final MarkOrderReadyUseCase markOrderReadyUseCase;
    private final DeliverOrderUseCase deliverOrderUseCase;
    private final CancelOrderUseCase cancelOrderUseCase;
    private final GetOrderByIdUseCase getOrderByIdUseCase;
    private final OrderResponseMapper orderResponseMapper;

    public OrderController(
            CreateOrderUseCase createOrderUseCase,
            AddItemToOrderUseCase addItemToOrderUseCase,
            ConfirmOrderUseCase confirmOrderUseCase,
            PayOrderUseCase payOrderUseCase,
            StartPreparationUseCase startPreparationUseCase,
            MarkOrderReadyUseCase markOrderReadyUseCase,
            DeliverOrderUseCase deliverOrderUseCase,
            CancelOrderUseCase cancelOrderUseCase,
            GetOrderByIdUseCase getOrderByIdUseCase,
            OrderResponseMapper orderResponseMapper
    ) {
        this.createOrderUseCase = createOrderUseCase;
        this.addItemToOrderUseCase = addItemToOrderUseCase;
        this.confirmOrderUseCase = confirmOrderUseCase;
        this.payOrderUseCase = payOrderUseCase;
        this.startPreparationUseCase = startPreparationUseCase;
        this.markOrderReadyUseCase = markOrderReadyUseCase;
        this.deliverOrderUseCase = deliverOrderUseCase;
        this.cancelOrderUseCase = cancelOrderUseCase;
        this.getOrderByIdUseCase = getOrderByIdUseCase;
        this.orderResponseMapper = orderResponseMapper;
    }

    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(@RequestBody CreateOrderRequestDto request) {
        Order order = createOrderUseCase.execute(request.getOrderId(), request.getCustomerId());
        OrderResponseDto response = orderResponseMapper.toDto(order);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{orderId}")
                .buildAndExpand(order.getId())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @PostMapping("/{orderId}/items")
    public ResponseEntity<OrderResponseDto> addItem(
            @PathVariable String orderId,
            @RequestBody AddItemToOrderRequestDto request
    ) {
        Order order = addItemToOrderUseCase.execute(orderId, request.getProductId(), request.getQuantity());
        return ResponseEntity.ok(orderResponseMapper.toDto(order));
    }

    @PostMapping("/{orderId}/confirm")
    public ResponseEntity<OrderResponseDto> confirmOrder(@PathVariable String orderId) {
        Order order = confirmOrderUseCase.execute(orderId);
        return ResponseEntity.ok(orderResponseMapper.toDto(order));
    }

    @PostMapping("/{orderId}/payment")
    public ResponseEntity<OrderResponseDto> payOrder(
            @PathVariable String orderId,
            @RequestBody PayOrderRequestDto request
    ) {
        Order order = payOrderUseCase.execute(orderId, request.getPaymentReference());
        return ResponseEntity.ok(orderResponseMapper.toDto(order));
    }

    @PostMapping("/{orderId}/preparation/start")
    public ResponseEntity<OrderResponseDto> startPreparation(@PathVariable String orderId) {
        Order order = startPreparationUseCase.execute(orderId);
        return ResponseEntity.ok(orderResponseMapper.toDto(order));
    }

    @PostMapping("/{orderId}/ready")
    public ResponseEntity<OrderResponseDto> markReady(@PathVariable String orderId) {
        Order order = markOrderReadyUseCase.execute(orderId);
        return ResponseEntity.ok(orderResponseMapper.toDto(order));
    }

    @PostMapping("/{orderId}/deliver")
    public ResponseEntity<OrderResponseDto> deliver(@PathVariable String orderId) {
        Order order = deliverOrderUseCase.execute(orderId);
        return ResponseEntity.ok(orderResponseMapper.toDto(order));
    }

    @PostMapping("/{orderId}/cancel")
    public ResponseEntity<OrderResponseDto> cancel(@PathVariable String orderId) {
        Order order = cancelOrderUseCase.execute(orderId);
        return ResponseEntity.ok(orderResponseMapper.toDto(order));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable String orderId) {
        Order order = getOrderByIdUseCase.execute(orderId);
        return ResponseEntity.status(HttpStatus.OK).body(orderResponseMapper.toDto(order));
    }
}
