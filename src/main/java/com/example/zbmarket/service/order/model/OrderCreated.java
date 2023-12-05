package com.example.zbmarket.service.order.model;

import com.example.zbmarket.repository.entity.MemberOrderEntity;
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
public class OrderCreated {
    private Long id;
    private OrderStatusEnum status;
    private Long price;
    private Long quantity;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<OrderProductCreated> orderProducts;

    public static OrderCreated from(MemberOrderEntity memberOrder) {
        return OrderCreated.builder()
                .id(memberOrder.getId())
                .status(memberOrder.getStatus())
                .price(memberOrder.getPrice())
                .quantity(memberOrder.getQuantity())
                .createdAt(memberOrder.getCreatedAt())
                .updatedAt(memberOrder.getUpdatedAt())
                .orderProducts(memberOrder.getOrderProducts().stream()
                        .map(OrderProductCreated::from)
                        .collect(Collectors.toList()))
                .build();
    }
}
