package dev.mark.jewelsstorebackend.cart.item;

public class CartItemException extends RuntimeException {

    public CartItemException(String message) {
        super(message);
    }

    public CartItemException(String message, Throwable cause) {
        super(message, cause);
    }

}