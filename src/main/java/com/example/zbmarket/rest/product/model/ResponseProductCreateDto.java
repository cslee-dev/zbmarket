package com.example.zbmarket.rest.product.model;

import com.example.zbmarket.service.product.model.ProductCreated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class ResponseProductCreateDto {
    private Long id;
    private String name;
    private Long stock;
    private Long price;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public static ResponseProductCreateDto from(ProductCreated product) {
        return ResponseProductCreateDto.builder()
                .id(product.getId())
                .name(product.getName())
                .stock(product.getStock())
                .price(product.getPrice())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }
}
