package com.example.zbmarket.rest.product.model;

import com.example.zbmarket.service.product.model.ProductFoundList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ResponseProductFoundListDto {
    private Long id;
    private String name;
    private Long stock;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public static ResponseProductFoundListDto from(ProductFoundList product) {
        return ResponseProductFoundListDto.builder()
                .id(product.getId())
                .name(product.getName())
                .stock(product.getStock())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }
}
