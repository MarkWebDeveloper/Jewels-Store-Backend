package dev.mark.jewelsstorebackend.cart;

import org.springframework.lang.NonNull;

import dev.mark.jewelsstorebackend.messages.Message;

public interface IGenericCartService<T> {
    public T getById(@NonNull Long id)throws Exception;
    public T addToCart(Long productId, Long quantity);
    public Message removeFromCart(Long productId);
}
