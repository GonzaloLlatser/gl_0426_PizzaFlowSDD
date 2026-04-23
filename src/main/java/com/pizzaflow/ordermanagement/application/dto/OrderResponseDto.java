package com.pizzaflow.ordermanagement.application.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class OrderResponseDto {

    private String orderId;
    private String customerId;
    private String customerName;
    private String status;
    private BigDecimal total;
    private String paymentReference;
    private List<OrderItemResponseDto> items = new ArrayList<>();

    public OrderResponseDto() {
    }

    public OrderResponseDto(
            String orderId,
            String customerId,
            String customerName,
            String status,
            BigDecimal total,
            String paymentReference,
            List<OrderItemResponseDto> items
    ) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.customerName = customerName;
        this.status = status;
        this.total = total;
        this.paymentReference = paymentReference;
        this.items = items;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getPaymentReference() {
        return paymentReference;
    }

    public void setPaymentReference(String paymentReference) {
        this.paymentReference = paymentReference;
    }

    public List<OrderItemResponseDto> getItems() {
        return items;
    }

    public void setItems(List<OrderItemResponseDto> items) {
        this.items = items;
    }
}
