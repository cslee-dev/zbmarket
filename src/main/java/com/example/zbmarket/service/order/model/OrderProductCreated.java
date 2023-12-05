package com.example.zbmarket.service.order.model;

import com.example.zbmarket.repository.entity.OrderProductEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class OrderProductCreated {
    private Long id;
    private String productName;
    private Long price;
    private Long quantity;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static OrderProductCreated from(OrderProductEntity orderProduct) {
        return OrderProductCreated.builder()
                .id(orderProduct.getId())
                .productName(orderProduct.getProductName())
                .price(orderProduct.getPrice())
                .quantity(orderProduct.getQuantity())
                .createdAt(orderProduct.getCreatedAt())
                .updatedAt(orderProduct.getUpdatedAt())
                .build();
    }
}
