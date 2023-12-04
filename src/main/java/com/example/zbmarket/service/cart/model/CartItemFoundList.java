package com.example.zbmarket.service.cart.model;

import com.example.zbmarket.repository.entity.CartItemEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class CartItemFoundList {
    private Long id;
    private Long cartId;
    private Long productId;
    private String productName;
    private Long quantity;


    public static CartItemFoundList from(CartItemEntity cartItem) {
        return CartItemFoundList.builder()
                .id(cartItem.getId())
                .cartId(cartItem.getCart().getId())
                .productId(cartItem.getCart().getId())
                .productName(cartItem.getProductName())
                .quantity(cartItem.getQuantity())
                .build();
    }
}
