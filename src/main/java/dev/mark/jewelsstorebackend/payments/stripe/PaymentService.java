package dev.mark.jewelsstorebackend.payments.stripe;

import org.springframework.stereotype.Service;
import com.stripe.exception.StripeException;

import dev.mark.jewelsstorebackend.config.payments.stripe.StripeConfiguration;
import dev.mark.jewelsstorebackend.payments.stripe.facades.PaymentFacade;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PaymentService {

    StripeConfiguration stripeConfig;
    PaymentFacade paymentFacade;

    public PaymentResponse createPaymentIntent(String paymentProvider, Cart cart) throws StripeException {

        PaymentResponse response = paymentFacade.createPaymentIntent("stripe", cart);

        return response;
    }
}
