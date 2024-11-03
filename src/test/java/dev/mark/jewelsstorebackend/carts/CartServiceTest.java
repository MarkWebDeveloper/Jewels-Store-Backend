package dev.mark.jewelsstorebackend.carts;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import dev.mark.jewelsstorebackend.cart.Cart;
import dev.mark.jewelsstorebackend.cart.CartRepository;
import dev.mark.jewelsstorebackend.cart.CartService;
import dev.mark.jewelsstorebackend.cart.item.CartItem;
import dev.mark.jewelsstorebackend.cart.item.CartItemRepository;
import dev.mark.jewelsstorebackend.cart.item.CartItemService;
import dev.mark.jewelsstorebackend.products.Product;
import dev.mark.jewelsstorebackend.products.ProductNotFoundException;
import dev.mark.jewelsstorebackend.products.ProductRepository;
import dev.mark.jewelsstorebackend.profiles.Profile;
import dev.mark.jewelsstorebackend.profiles.ProfileNotFoundException;
import dev.mark.jewelsstorebackend.profiles.ProfileRepository;
import dev.mark.jewelsstorebackend.utilities.SecurityUtils;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {

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

    @Mock
    private Authentication authentication;

    @Mock
    SecurityUtils securityUtils;

    @InjectMocks
    CartService cartService;

    private MockedStatic<SecurityUtils> mockedSecurityUtils;

    private Cart cart;
    private Product product1;
    private Product product2;
    CartItem cartItem1;
    CartItem cartItem2;
    Profile profile;

    {
        product1 = Product.builder().id(1L).productName("Product1").build();
        product2 = Product.builder().id(1L).productName("Product2").build();

        cart = Cart.builder().id(1L).build();

        cartItem1 = CartItem.builder().id(1L).product(product1).quantity(1L).cart(cart).build();
        cartItem2 = CartItem.builder().id(1L).product(product1).quantity(2L).cart(cart).build();

        List<CartItem> cartItems = new ArrayList<>();
        cartItems.add(cartItem1);

        cart.setCartItems(cartItems);

        profile = new Profile();
        profile.setCart(cart);
    }

    @BeforeEach
    public void setUp() {
        mockedSecurityUtils = mockStatic(SecurityUtils.class);
        mockedSecurityUtils.when(SecurityUtils::getAuthentication).thenReturn(authentication);
        lenient().when(authentication.getName()).thenReturn("testuser@example.com");
    }

    @AfterEach
    public void tearDown() {
        mockedSecurityUtils.close();
    }

    @Test
    void test_GetsCartById() {
        when(cartRepository.findById(1L)).thenReturn(Optional.of(cart));

        Cart result = cartService.getById(1L);

        verify(cartRepository).findById(1L);

        assertEquals(result, cart);
        assertTrue(result.getCartItems().contains(cartItem1));
    }

    @Test
    void test_AddsToCart() {

        when(profileRepository.findByEmail("testuser@example.com")).thenReturn(Optional.of(profile));
        when(productRepository.findById(2L)).thenReturn(Optional.of(product2));
        when(cartItemService.save(product2, 2L, cart)).thenReturn(cartItem2);

        Cart updatedCart = cartService.addToCart(2L, 2L);

        verify(authentication).getName();
        verify(profileRepository).findByEmail("testuser@example.com");
        verify(productRepository).findById(2L);
        verify(cartItemService).save(product2, 2L, cart);
        verify(profileRepository).save(profile);

        assertNotNull(updatedCart);
        assertEquals(2, updatedCart.getCartItems().size());
        assertEquals(cartItem2, updatedCart.getCartItems().get(1));
        assertEquals(2L, updatedCart.getCartItems().get(1).getQuantity());
    }

    @Test
    public void testAddToCart_ProfileNotFound() {

        when(profileRepository.findByEmail("testuser@example.com")).thenReturn(Optional.empty());

        assertThrows(ProfileNotFoundException.class, () -> cartService.addToCart(1L, 2L));
    }

    @Test

    public void testAddToCart_ProductNotFound() {

        when(profileRepository.findByEmail("testuser@example.com")).thenReturn(Optional.of(profile));

        when(productRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> cartService.addToCart(2L, 2L));
    }
}
