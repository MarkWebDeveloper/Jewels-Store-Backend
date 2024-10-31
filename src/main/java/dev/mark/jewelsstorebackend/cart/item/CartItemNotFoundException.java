package dev.mark.jewelsstorebackend.cart.item;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Cart item not found")
public class CartItemNotFoundException extends CartItemException {

    public CartItemNotFoundException(String message) {
        super(message);
    }

    public CartItemNotFoundException(String message, Throwable cause) {
        super(message,cause);
    }
    
}