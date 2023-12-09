package com.example.zbmarket.repository.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
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

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
