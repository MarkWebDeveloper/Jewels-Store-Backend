package dev.mark.jewelsstorebackend.facades.payments;

import org.springframework.stereotype.Component;

import com.stripe.exception.StripeException;

import dev.mark.jewelsstorebackend.payments.stripe.PaymentRequest;
import dev.mark.jewelsstorebackend.payments.stripe.PaymentResponse;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class PaymentFacade implements IPaymentFacade<PaymentResponse> {

    StripePayment stripePayment;

    public PaymentResponse createPaymentIntent(String type, PaymentRequest payment) throws StripeException {

        PaymentResponse response = new PaymentResponse();

        if (type == "stripe") response = stripePayment.createPaymentIntent(payment);

        return response;
    }
}
