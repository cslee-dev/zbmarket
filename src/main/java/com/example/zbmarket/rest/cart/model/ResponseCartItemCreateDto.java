package com.example.zbmarket.rest.cart.model;

import com.example.zbmarket.service.cart.model.CartItemCreated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ResponseCartItemCreateDto {
    private Long id;
    private Long cartId;
    private Long productId;
    private String productName;
    private Long quantity;


    public static ResponseCartItemCreateDto from(CartItemCreated cartItem) {
        return ResponseCartItemCreateDto.builder()
                .id(cartItem.getId())
                .cartId(cartItem.getCartId())
                .productId(cartItem.getProductId())
                .productName(cartItem.getProductName())
                .quantity(cartItem.getQuantity())
                .build();
    }
}
