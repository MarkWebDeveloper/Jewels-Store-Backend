package dev.mark.jewelsstorebackend.facades.payments;

import org.springframework.stereotype.Component;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;

import dev.mark.jewelsstorebackend.config.payments.stripe.StripeConfiguration;
import dev.mark.jewelsstorebackend.payments.stripe.Calculator;
import dev.mark.jewelsstorebackend.payments.stripe.PaymentRequest;
import dev.mark.jewelsstorebackend.payments.stripe.PaymentResponse;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class StripePayment implements IPayment<PaymentResponse> {

    StripeConfiguration stripeConfig;

    public PaymentResponse createPaymentIntent(PaymentRequest payment) throws StripeException {

        Stripe.apiKey = stripeConfig.getSecretKey();

        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount(Calculator.calculateOrderAmount(payment.getItems()))
                .setCurrency("eur")
                .setAutomaticPaymentMethods(
                        PaymentIntentCreateParams.AutomaticPaymentMethods
                                .builder()
                                .setEnabled(true)
                                .build())
                .build();

        PaymentIntent paymentIntent = PaymentIntent.create(params);

        PaymentResponse paymentResponse = new PaymentResponse(paymentIntent.getClientSecret());

        return paymentResponse;
    }
}