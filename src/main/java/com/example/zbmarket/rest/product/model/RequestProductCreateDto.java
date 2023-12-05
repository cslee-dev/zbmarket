package com.example.zbmarket.rest.product.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class RequestProductCreateDto {
    private String name;
    private Long stock;
    private Long price;
}
