package com.example.zbmarket.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "order_product")
public class OrderProductEntity {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private MemberOrderEntity order;

    private String productName;
    private Long price;
    private Long quantity;

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    public static OrderProductEntity createNewOrderProduct(
            ProductEntity productEntity, Long quantity, Long price) {
        return OrderProductEntity.builder()
                .productName(productEntity.getName())
                .quantity(quantity)
                .price(price)
                .build();
    }
}
