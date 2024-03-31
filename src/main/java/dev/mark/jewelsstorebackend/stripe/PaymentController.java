package dev.mark.jewelsstorebackend.stripe;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping(path = "${api-endpoint}/payments")
public class PaymentController {

    PaymentService service;

    @PostMapping(path = "/create-payment-intent")
    public ResponseEntity<PaymentResponse> create(@RequestBody Payment payment) throws StripeException {

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

        return ResponseEntity.status(201).body(paymentResponse);
    }
}
