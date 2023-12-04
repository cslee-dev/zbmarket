package com.example.zbmarket.rest.cart.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class RequestCartItemCreateDto {
    private Long quantity;
}
