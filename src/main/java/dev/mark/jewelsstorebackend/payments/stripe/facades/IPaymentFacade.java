package dev.mark.jewelsstorebackend.payments.stripe.facades;

import dev.mark.jewelsstorebackend.payments.stripe.PaymentRequest;

public interface IPaymentFacade<T> {
    T createPaymentIntent(String type, PaymentRequest request) throws Exception;
}