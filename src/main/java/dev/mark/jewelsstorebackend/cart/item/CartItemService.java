package dev.mark.jewelsstorebackend.cart.item;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import dev.mark.jewelsstorebackend.cart.Cart;
import dev.mark.jewelsstorebackend.messages.Message;
import dev.mark.jewelsstorebackend.products.Product;
import dev.mark.jewelsstorebackend.products.ProductRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CartItemService {

    CartItemRepository cartItemRepository;
    ProductRepository productRepository;

    public CartItem getById(@NonNull Long id) {

        return cartItemRepository.findById(id).orElseThrow(() -> new CartItemNotFoundException("Cart item not found"));
    }

    public CartItem save(@NonNull Product product, @NonNull Long quantity, Cart cart) {

        CartItem newCartItem = CartItem.builder().product(product).quantity(quantity).cart(cart).build();

        return cartItemRepository.save(newCartItem);
    }

    public Message delete(@NonNull Long cartItemId) {

        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(() -> new CartItemNotFoundException("Cart item not found while attempting to delete it"));

        cartItemRepository.delete(cartItem);

        Message message = new Message();

        message.createMessage("Cart item with the name '" + cartItem.getProduct().getProductName() + "' is deleted from the cart items table");

        return message;
    }
}
