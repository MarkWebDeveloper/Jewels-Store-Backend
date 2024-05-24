package dev.mark.jewelsstorebackend.payments.stripe.facades;

import dev.mark.jewelsstorebackend.payments.stripe.PaymentRequest;

public interface IPayment<T> {
    T createPaymentIntent(PaymentRequest request) throws Exception;
}