package dev.mark.jewelsstorebackend.cart;

import java.util.List;

import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import dev.mark.jewelsstorebackend.cart.item.CartItem;
import dev.mark.jewelsstorebackend.cart.item.CartItemService;
import dev.mark.jewelsstorebackend.products.Product;
import dev.mark.jewelsstorebackend.products.ProductNotFoundException;
import dev.mark.jewelsstorebackend.products.ProductRepository;
import dev.mark.jewelsstorebackend.profiles.Profile;
import dev.mark.jewelsstorebackend.profiles.ProfileNotFoundException;
import dev.mark.jewelsstorebackend.profiles.ProfileRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CartService implements IGenericCartService<Cart> {
    
    CartRepository repository;
    ProfileRepository profileRepository;
    ProductRepository productRepository;
    CartItemService cartItemService;

    public Cart getById(@NonNull Long id)throws Exception{
        Cart cart = repository.findById(id).orElseThrow(() -> new CartNotFoundException("Profile not found"));

        return cart;
    }

    public Cart addToCart(Long productId, Long quantity) {

        SecurityContext contextHolder = SecurityContextHolder.getContext();
        Authentication auth = contextHolder.getAuthentication();
        
        Profile updatingProfile = profileRepository.findByEmail(auth.getName()).orElseThrow(() -> new ProfileNotFoundException("Profile not found"));
        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("Product not found"));
        
        CartItem savedCartItem = cartItemService.save(product, quantity);

        List<CartItem> cartItems = updatingProfile.getCart().getCartItems();
        cartItems.add(savedCartItem);

        updatingProfile.getCart().setCartItems(cartItems);

        return updatingProfile.getCart();
    }

}
