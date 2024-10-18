package dev.mark.jewelsstorebackend.cart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class CartItem {
    private String productName;
    private String productDescription;
    private Long price;
    private Long categoryId;
    private Long quantity;
}