package com.pizzaflow.ordermanagement.infrastructure.web;

import com.pizzaflow.ordermanagement.domain.exception.BusinessException;
import com.pizzaflow.ordermanagement.domain.exception.CancelledOrderCannotTransitionException;
import com.pizzaflow.ordermanagement.domain.exception.DeliveredOrderCannotBeCancelledException;
import com.pizzaflow.ordermanagement.domain.exception.DuplicatePaymentNotAllowedException;
import com.pizzaflow.ordermanagement.domain.exception.OnlyOrdersInPreparationCanBeMarkedReadyException;
import com.pizzaflow.ordermanagement.domain.exception.OnlyReadyOrdersCanBeDeliveredException;
import com.pizzaflow.ordermanagement.domain.exception.OrderCannotBeConfirmedWhenEmptyException;
import com.pizzaflow.ordermanagement.domain.exception.OrderItemQuantityMustBeGreaterThanZeroException;
import com.pizzaflow.ordermanagement.domain.exception.OrderModificationNotAllowedAfterConfirmationException;
import com.pizzaflow.ordermanagement.domain.exception.OrderMustBeConfirmedBeforePaymentException;
import com.pizzaflow.ordermanagement.domain.exception.OrderMustBePaidBeforePreparationException;
import com.pizzaflow.ordermanagement.domain.exception.OrderTotalMustMatchItemSubtotalSumException;
import com.pizzaflow.ordermanagement.domain.exception.ProductMustBeAvailableException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            OrderCannotBeConfirmedWhenEmptyException.class,
            OrderMustBeConfirmedBeforePaymentException.class,
            OrderMustBePaidBeforePreparationException.class,
            OnlyOrdersInPreparationCanBeMarkedReadyException.class,
            OnlyReadyOrdersCanBeDeliveredException.class,
            DeliveredOrderCannotBeCancelledException.class,
            CancelledOrderCannotTransitionException.class,
            ProductMustBeAvailableException.class,
            OrderItemQuantityMustBeGreaterThanZeroException.class,
            OrderTotalMustMatchItemSubtotalSumException.class,
            OrderModificationNotAllowedAfterConfirmationException.class,
            DuplicatePaymentNotAllowedException.class
    })
    public ResponseEntity<ApiErrorResponse> handleBusinessException(BusinessException exception) {
        return buildResponse(HttpStatus.CONFLICT, exception.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiErrorResponse> handleIllegalArgumentException(IllegalArgumentException exception) {
        return buildResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ApiErrorResponse> handleIllegalStateException(IllegalStateException exception) {
        return buildResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleUnexpectedException(Exception exception) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected server error.");
    }

    private ResponseEntity<ApiErrorResponse> buildResponse(HttpStatus status, String message) {
        ApiErrorResponse response = new ApiErrorResponse(
                Instant.now(),
                status.value(),
                status.getReasonPhrase(),
                message
        );
        return ResponseEntity.status(status).body(response);
    }
}
