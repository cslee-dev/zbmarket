package com.example.zbmarket.repository.entity;

import com.example.zbmarket.service.order.OrderAccumulator;
import com.example.zbmarket.type.order.OrderStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "member_order")
public class MemberOrderEntity {
    @Id
    @GeneratedValue
    private Long id;
    @Enumerated(EnumType.STRING)
    private OrderStatusEnum status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderProductEntity> orderProducts;
    private Long price;
    private Long quantity;

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;


    public static MemberOrderEntity createNewOrder(MemberEntity memberEntity
            , List<OrderProductEntity> orderProducts, OrderAccumulator accumulator) {
        return MemberOrderEntity.builder()
                .status(OrderStatusEnum.ORDERED)
                .member(memberEntity)
                .orderProducts(orderProducts)
                .price(accumulator.getPrice())
                .quantity(accumulator.getQuantity())
                .build();
    }

}
