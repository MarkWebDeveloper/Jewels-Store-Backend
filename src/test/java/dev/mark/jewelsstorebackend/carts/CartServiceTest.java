package dev.mark.jewelsstorebackend.carts;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import dev.mark.jewelsstorebackend.cart.Cart;
import dev.mark.jewelsstorebackend.cart.CartRepository;
import dev.mark.jewelsstorebackend.cart.CartService;
import dev.mark.jewelsstorebackend.cart.item.CartItem;
import dev.mark.jewelsstorebackend.cart.item.CartItemRepository;
import dev.mark.jewelsstorebackend.cart.item.CartItemService;
import dev.mark.jewelsstorebackend.products.Product;
import dev.mark.jewelsstorebackend.products.ProductRepository;
import dev.mark.jewelsstorebackend.profiles.ProfileRepository;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {

    @InjectMocks
    CartService cartService;

    @Mock
    CartRepository cartRepository;

    @Mock
    CartItemRepository cartItemRepository;

    @Mock
    ProfileRepository profileRepository;

    @Mock
    ProductRepository productRepository;

    @Mock
    CartItemService cartItemService;

    private Cart cart;
    private Product product1;
    CartItem cartItem1;

    {
        product1 = Product.builder().id(1L).productName("Product1").build();
        cart = Cart.builder().id(1L).build();
        cartItem1 = CartItem.builder().id(1L).product(product1).cart(cart).build();
        List<CartItem> cartItems = new ArrayList<>();
        cartItems.add(cartItem1);
        cart.setCartItems(cartItems);
    }

    @Test
    void test_GetCartById() {
        when(cartRepository.findById(1L)).thenReturn(Optional.of(cart));

        Cart result = cartService.getById(1L);
        
        assertThat(result, is(cart));
        assertThat(result.getCartItems(), contains(cartItem1));
    }
}
