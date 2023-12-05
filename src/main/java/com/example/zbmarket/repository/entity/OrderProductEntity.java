package com.example.zbmarket.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Entity
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

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
