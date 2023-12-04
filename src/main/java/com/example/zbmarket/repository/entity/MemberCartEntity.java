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
@Table(name = "member_cart")
public class MemberCartEntity {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", unique = true)
    private MemberEntity member;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
