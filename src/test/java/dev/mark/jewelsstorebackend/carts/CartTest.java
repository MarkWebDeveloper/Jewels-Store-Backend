package dev.mark.jewelsstorebackend.carts;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import dev.mark.jewelsstorebackend.cart.Cart;
import dev.mark.jewelsstorebackend.cart.item.CartItem;
import dev.mark.jewelsstorebackend.products.Product;

public class CartTest {

    private Cart cart;
    private Product product1;

    {
        cart = Cart.builder().id(1L).build();
        product1 = Product.builder().id(1L).productName("Product1").build();
        CartItem cartItem1 = new CartItem(1L, product1, 1L, cart);
        List<CartItem> cartItems = new ArrayList<>();
        cartItems.add(cartItem1);
        cart.setCartItems(cartItems);
    }

    @Test
    void test_cartHasIdAndCartItems() {
        assertThat(cart.getId(), is(1L));
        assertThat(cart.getCartItems().get(0).getProduct().getProductName(), is("Product1"));
    }
}
