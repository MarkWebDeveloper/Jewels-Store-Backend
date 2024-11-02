package dev.mark.jewelsstorebackend.cart;

import java.util.List;

import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import dev.mark.jewelsstorebackend.cart.item.CartItem;
import dev.mark.jewelsstorebackend.cart.item.CartItemNotFoundException;
import dev.mark.jewelsstorebackend.cart.item.CartItemRepository;
import dev.mark.jewelsstorebackend.cart.item.CartItemService;
import dev.mark.jewelsstorebackend.messages.Message;
import dev.mark.jewelsstorebackend.products.Product;
import dev.mark.jewelsstorebackend.products.ProductNotFoundException;
import dev.mark.jewelsstorebackend.products.ProductRepository;
import dev.mark.jewelsstorebackend.profiles.Profile;
import dev.mark.jewelsstorebackend.profiles.ProfileNotFoundException;
import dev.mark.jewelsstorebackend.profiles.ProfileRepository;
import dev.mark.jewelsstorebackend.utilities.SecurityUtils;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CartService implements IGenericCartService<Cart> {
    
    CartRepository cartRepository;
    CartItemRepository cartItemRepository;
    ProfileRepository profileRepository;
    ProductRepository productRepository;
    CartItemService cartItemService;

    public Cart getById(@NonNull Long id) {
        Cart cart = cartRepository.findById(id).orElseThrow(() -> new CartNotFoundException("Profile not found"));

        return cart;
    }

    public Cart addToCart(Long productId, Long quantity) {

        Authentication auth = SecurityUtils.getAuthentication();
        
        Profile updatingProfile = profileRepository.findByEmail(auth.getName()).orElseThrow(() -> new ProfileNotFoundException("Profile not found"));
        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("Product not found"));
        
        Cart cart = updatingProfile.getCart();
        CartItem savedCartItem = cartItemService.save(product, quantity, cart);

        List<CartItem> cartItems = cart.getCartItems();
    
        cartItems.add(savedCartItem);
        cart.setCartItems(cartItems);

        updatingProfile.setCart(cart);
        profileRepository.save(updatingProfile);

        return updatingProfile.getCart();
    }

    public Message removeFromCart(Long cartItemId) {

        Authentication auth = SecurityUtils.getAuthentication();
        
        Profile updatingProfile = profileRepository.findByEmail(auth.getName()).orElseThrow(() -> new ProfileNotFoundException("Profile not found while attempting to remove an item from cart"));
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(() -> new CartItemNotFoundException("Cart item not found while attempting to remove an item from cart"));
        
        List<CartItem> cartItems = updatingProfile.getCart().getCartItems();
        cartItems.remove(cartItem);
        
        updatingProfile.getCart().setCartItems(cartItems);

        return cartItemService.delete(cartItemId);
    }

}
