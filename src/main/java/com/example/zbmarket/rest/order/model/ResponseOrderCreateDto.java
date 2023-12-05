package com.example.zbmarket.rest.order.model;

import com.example.zbmarket.service.order.model.OrderCreated;
import com.example.zbmarket.type.order.OrderStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class ResponseOrderCreateDto {
    private Long id;
    private OrderStatusEnum status;
    private Long price;
    private Long quantity;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<ResponseOrderProductCreateDto> orderProducts;

    public static ResponseOrderCreateDto from(OrderCreated orderCreated) {
        return ResponseOrderCreateDto.builder()
                .id(orderCreated.getId())
                .status(orderCreated.getStatus())
                .price(orderCreated.getPrice())
                .quantity(orderCreated.getQuantity())
                .createdAt(orderCreated.getCreatedAt())
                .updatedAt(orderCreated.getUpdatedAt())
                .orderProducts(orderCreated.getOrderProducts().stream()
                        .map(ResponseOrderProductCreateDto::from)
                        .collect(Collectors.toList()))
                .build();
    }


}
