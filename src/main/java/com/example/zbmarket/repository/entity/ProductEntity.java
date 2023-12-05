package com.example.zbmarket.repository.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Entity
@Table(name = "product")
public class ProductEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private Long price;
    private Long stock;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<CartItemEntity> cartItems;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
