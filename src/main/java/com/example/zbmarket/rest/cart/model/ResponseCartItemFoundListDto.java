package com.example.zbmarket.rest.cart.model;

import com.example.zbmarket.service.cart.model.CartItemFoundList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ResponseCartItemFoundListDto {
    private Long id;
    private Long cartId;
    private Long productId;
    private String productName;
    private Long quantity;


    public static ResponseCartItemFoundListDto from(CartItemFoundList cartItem) {
        return ResponseCartItemFoundListDto.builder()
                .id(cartItem.getId())
                .cartId(cartItem.getCartId())
                .productId(cartItem.getProductId())
                .productName(cartItem.getProductName())
                .quantity(cartItem.getQuantity())
                .build();
    }
}
