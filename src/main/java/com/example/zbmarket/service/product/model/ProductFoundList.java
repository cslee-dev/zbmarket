package com.example.zbmarket.service.product.model;

import com.example.zbmarket.repository.entity.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ProductFoundList {
    private Long id;
    private String name;
    private Long stock;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ProductFoundList from(ProductEntity product) {
        return ProductFoundList.builder()
                .id(product.getId())
                .name(product.getName())
                .stock(product.getStock())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }
}
