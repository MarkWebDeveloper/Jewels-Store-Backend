package dev.mark.jewelsstorebackend.payments.stripe.facades;

import org.springframework.stereotype.Component;

import com.stripe.exception.StripeException;

import dev.mark.jewelsstorebackend.payments.stripe.Cart;
import dev.mark.jewelsstorebackend.payments.stripe.PaymentResponse;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class PaymentFacade implements IPaymentFacade<PaymentResponse> {

    StripePayment stripePayment;

    public PaymentResponse createPaymentIntent(String type, Cart cart) throws StripeException {

        PaymentResponse response = new PaymentResponse();

        if (type == "stripe") response = stripePayment.createPaymentIntent(cart);

        return response;
    }
}
