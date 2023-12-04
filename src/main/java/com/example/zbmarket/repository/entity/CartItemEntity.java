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
@Table(name = "cart_item", uniqueConstraints = {@UniqueConstraint(columnNames = {"cart_id", "product_id"})})
public class CartItemEntity {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private MemberCartEntity cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    private String productName;
    private Long quantity;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
