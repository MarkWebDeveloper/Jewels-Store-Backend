package dev.mark.jewelsstorebackend.payments.stripe.facades;

import dev.mark.jewelsstorebackend.payments.stripe.Cart;

public interface IPaymentFacade<T> {
    T createPaymentIntent(String type, Cart cart) throws Exception;
}