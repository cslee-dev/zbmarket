package com.example.zbmarket.service.product.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ProductCreated {
    private Long id;
    private String name;
    private Long stock;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
