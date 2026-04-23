package com.pizzaflow.ordermanagement.infrastructure.external;

import com.pizzaflow.ordermanagement.application.port.out.PaymentGateway;
import org.springframework.stereotype.Component;

@Component
public class StubPaymentGatewayAdapter implements PaymentGateway {

    @Override
    public boolean isPaymentSuccessful(String paymentReference) {
        return paymentReference != null && !paymentReference.isBlank();
    }
}
