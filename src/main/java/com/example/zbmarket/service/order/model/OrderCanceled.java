package com.example.zbmarket.service.order.model;

import com.example.zbmarket.repository.entity.MemberOrderEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class OrderCanceled {
    private Long id;


    public static OrderCanceled from(MemberOrderEntity memberOrderEntity) {
        return OrderCanceled.builder()
                .id(memberOrderEntity.getId())
                .build();
    }
}
