package dev.mark.jewelsstorebackend.cart.item;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CartItemService {
    CartItemRepository repository;

    public CartItem getById(@NonNull Long id)throws Exception{

        return repository.findById(id).orElseThrow(() -> new CartItemNotFoundException("Cart item not found"));
    }
}
