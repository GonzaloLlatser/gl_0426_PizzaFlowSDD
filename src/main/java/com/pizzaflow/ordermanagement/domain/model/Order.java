package com.pizzaflow.ordermanagement.domain.model;

import com.pizzaflow.ordermanagement.domain.exception.CancelledOrderCannotTransitionException;
import com.pizzaflow.ordermanagement.domain.exception.DeliveredOrderCannotBeCancelledException;
import com.pizzaflow.ordermanagement.domain.exception.DuplicatePaymentNotAllowedException;
import com.pizzaflow.ordermanagement.domain.exception.OnlyOrdersInPreparationCanBeMarkedReadyException;
import com.pizzaflow.ordermanagement.domain.exception.OnlyReadyOrdersCanBeDeliveredException;
import com.pizzaflow.ordermanagement.domain.exception.OrderCannotBeConfirmedWhenEmptyException;
import com.pizzaflow.ordermanagement.domain.exception.OrderModificationNotAllowedAfterConfirmationException;
import com.pizzaflow.ordermanagement.domain.exception.OrderMustBeConfirmedBeforePaymentException;
import com.pizzaflow.ordermanagement.domain.exception.OrderMustBePaidBeforePreparationException;
import com.pizzaflow.ordermanagement.domain.exception.OrderTotalMustMatchItemSubtotalSumException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Order {

    private final String id;
    private final Customer customer;
    private final List<OrderItem> items;
    private BigDecimal total;
    private OrderStatus status;
    private String paymentReference;

    public Order(String id, Customer customer) {
        this.id = requireNonBlank(id, "Order id must not be blank.");
        this.customer = requireCustomer(customer);
        this.items = new ArrayList<>();
        this.total = BigDecimal.ZERO;
        this.status = OrderStatus.CREATED;
        this.paymentReference = null;
    }

    public static Order reconstitute(
            String id,
            Customer customer,
            List<OrderItem> items,
            BigDecimal total,
            OrderStatus status,
            String paymentReference
    ) {
        Order order = new Order(id, customer);
        order.items.addAll(requireItems(items));
        order.total = requireNonNegative(total, "Order total must not be null or negative.");
        order.status = requireStatus(status);
        order.paymentReference = paymentReference;
        return order;
    }

    public String getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<OrderItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public BigDecimal getTotal() {
        return total;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public String getPaymentReference() {
        return paymentReference;
    }

    public boolean hasItems() {
        return !items.isEmpty();
    }

    public void addItem(Product product, int quantity) {
        ensureOrderCanBeModified();
        OrderItem existingItem = findItemByProductId(product.getId());
        if (existingItem == null) {
            items.add(new OrderItem(product, quantity));
        } else {
            existingItem.increaseQuantity(quantity);
        }
        recalculateTotal();
    }

    public void recalculateTotal() {
        BigDecimal calculatedTotal = BigDecimal.ZERO;
        for (OrderItem item : items) {
            calculatedTotal = calculatedTotal.add(item.getSubtotal());
        }

        if (calculatedTotal.signum() < 0) {
            throw new OrderTotalMustMatchItemSubtotalSumException();
        }

        this.total = calculatedTotal;
    }

    public void confirm() {
        ensureNotCancelled();
        if (status != OrderStatus.CREATED) {
            throw new OrderModificationNotAllowedAfterConfirmationException();
        }
        if (!hasItems()) {
            throw new OrderCannotBeConfirmedWhenEmptyException();
        }

        recalculateTotal();
        this.status = OrderStatus.CONFIRMED;
    }

    public void pay(String paymentReference) {
        ensureNotCancelled();
        if (status == OrderStatus.PAID) {
            throw new DuplicatePaymentNotAllowedException();
        }
        if (status != OrderStatus.CONFIRMED) {
            throw new OrderMustBeConfirmedBeforePaymentException();
        }

        this.paymentReference = requireNonBlank(paymentReference, "Payment reference must not be blank.");
        this.status = OrderStatus.PAID;
    }

    public void startPreparation() {
        ensureNotCancelled();
        if (status != OrderStatus.PAID) {
            throw new OrderMustBePaidBeforePreparationException();
        }

        this.status = OrderStatus.IN_PREPARATION;
    }

    public void markReady() {
        ensureNotCancelled();
        if (status != OrderStatus.IN_PREPARATION) {
            throw new OnlyOrdersInPreparationCanBeMarkedReadyException();
        }

        this.status = OrderStatus.READY;
    }

    public void deliver() {
        ensureNotCancelled();
        if (status != OrderStatus.READY) {
            throw new OnlyReadyOrdersCanBeDeliveredException();
        }

        this.status = OrderStatus.DELIVERED;
    }

    public void cancel() {
        if (status == OrderStatus.DELIVERED) {
            throw new DeliveredOrderCannotBeCancelledException();
        }
        if (status == OrderStatus.CANCELLED) {
            throw new CancelledOrderCannotTransitionException();
        }

        this.status = OrderStatus.CANCELLED;
    }

    private OrderItem findItemByProductId(String productId) {
        for (OrderItem item : items) {
            if (item.getProduct().getId().equals(productId)) {
                return item;
            }
        }
        return null;
    }

    private static String requireNonBlank(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(message);
        }
        return value;
    }

    private static Customer requireCustomer(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer must not be null.");
        }
        return customer;
    }

    private static List<OrderItem> requireItems(List<OrderItem> items) {
        if (items == null) {
            throw new IllegalArgumentException("Order items must not be null.");
        }
        return items;
    }

    private static BigDecimal requireNonNegative(BigDecimal value, String message) {
        if (value == null || value.signum() < 0) {
            throw new IllegalArgumentException(message);
        }
        return value;
    }

    private static OrderStatus requireStatus(OrderStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("Order status must not be null.");
        }
        return status;
    }

    private void ensureOrderCanBeModified() {
        ensureNotCancelled();
        if (status != OrderStatus.CREATED) {
            throw new OrderModificationNotAllowedAfterConfirmationException();
        }
    }

    private void ensureNotCancelled() {
        if (status == OrderStatus.CANCELLED) {
            throw new CancelledOrderCannotTransitionException();
        }
    }
}
