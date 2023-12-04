package com.example.zbmarket.service.cart.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class CartItemCreated {
    private Long id;
    private Long cartId;
    private Long productId;
    private String productName;
    private Long quantity;
}
