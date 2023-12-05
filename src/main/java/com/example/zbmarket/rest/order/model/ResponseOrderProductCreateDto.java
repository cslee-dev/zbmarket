package com.example.zbmarket.rest.order.model;

import com.example.zbmarket.service.order.model.OrderProductCreated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class ResponseOrderProductCreateDto {
    private Long id;
    private String productName;
    private Long price;
    private Long quantity;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ResponseOrderProductCreateDto from(OrderProductCreated orderProductCreated) {
        return ResponseOrderProductCreateDto.builder()
                .id(orderProductCreated.getId())
                .productName(orderProductCreated.getProductName())
                .price(orderProductCreated.getPrice())
                .quantity(orderProductCreated.getQuantity())
                .createdAt(orderProductCreated.getCreatedAt())
                .updatedAt(orderProductCreated.getUpdatedAt())
                .build();
    }
}
