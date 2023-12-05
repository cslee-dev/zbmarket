package com.example.zbmarket.rest.order.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class RequestOrderProductCreateDto {
    private Long id;
    private Long quantity;
}
