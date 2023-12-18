package com.example.zbmarket.rest.order.model;

import com.example.zbmarket.service.order.model.OrderCanceled;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ResponseOrderCancelDto {
    private Long id;

    public static ResponseOrderCancelDto from(OrderCanceled orderCanceled) {
        return ResponseOrderCancelDto.builder()
                .id(orderCanceled.getId())
                .build();
    }
}
