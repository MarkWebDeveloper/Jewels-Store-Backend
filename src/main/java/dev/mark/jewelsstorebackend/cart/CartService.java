package dev.mark.jewelsstorebackend.cart;

import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CartService {
    
    CartRepository repository;

    @PreAuthorize("hasRole('USER')")
    public Cart getById(@NonNull Long id)throws Exception{
        Cart cart = repository.findById(id).orElseThrow(() -> new CartNotFoundException("Profile not found"));

        return cart;
    }
}
