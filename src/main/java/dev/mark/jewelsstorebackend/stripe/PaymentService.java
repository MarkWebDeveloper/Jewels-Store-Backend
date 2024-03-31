package dev.mark.jewelsstorebackend.stripe;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;

public class PaymentService {

    public PaymentResponse createPaymentIntent(Payment payment) throws StripeException {
        Stripe.apiKey = "${STRIPE_SECRET_KEY}";

        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount(Calculator.calculateOrderAmount(payment.getItems()))
                .setCurrency("eur")
                // In the latest version of the API, specifying the `automatic_payment_methods`
                // parameter is optional because Stripe enables its functionality by default.
                .setAutomaticPaymentMethods(
                        PaymentIntentCreateParams.AutomaticPaymentMethods
                                .builder()
                                .setEnabled(true)
                                .build())
                .build();

        // Create a PaymentIntent with the order amount and currency
        PaymentIntent paymentIntent = PaymentIntent.create(params);

        PaymentResponse paymentResponse = new PaymentResponse(paymentIntent.getClientSecret());

        return paymentResponse;
    }
}
