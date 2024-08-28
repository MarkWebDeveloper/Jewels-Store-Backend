package dev.mark.jewelsstorebackend.payments.stripe.facades;

import dev.mark.jewelsstorebackend.payments.stripe.Cart;

public interface IPayment<T> {
    T createPaymentIntent(Cart cart) throws Exception;
}