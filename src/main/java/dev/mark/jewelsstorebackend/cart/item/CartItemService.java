package dev.mark.jewelsstorebackend.cart.item;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import dev.mark.jewelsstorebackend.products.Product;
import dev.mark.jewelsstorebackend.products.ProductRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CartItemService {

    CartItemRepository cartRepository;
    ProductRepository productRepository;

    public CartItem getById(@NonNull Long id) {

        return cartRepository.findById(id).orElseThrow(() -> new CartItemNotFoundException("Cart item not found"));
    }

    public CartItem save(@NonNull Product product, @NonNull Long quantity) {

        CartItem newCartItem = CartItem.builder().product(product).quantity(quantity).build();

        return cartRepository.save(newCartItem);
    }
}
