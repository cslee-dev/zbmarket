package com.example.zbmarket.rest.order.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class RequestOrderCreateDto {
    private List<RequestOrderProductCreateDto> orderProducts;
}
