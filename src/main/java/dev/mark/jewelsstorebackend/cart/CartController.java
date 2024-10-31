package dev.mark.jewelsstorebackend.cart;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "${api-endpoint}")
public class CartController {

    IGenericCartService<Cart> service;

    public CartController(CartService service) {
        this.service = service;
    }

    @PutMapping(path = "/user/cart/addToCart/{id}/{quantity}")
    public ResponseEntity<Cart> addItem(@PathVariable("id") Long id, @PathVariable("quantity") Long quantity) throws Exception {

        Cart cart = service.addToCart(id, quantity);

        return ResponseEntity.status(200).body(cart);
    }
}
