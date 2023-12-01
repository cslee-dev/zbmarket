package com.example.zbmarket.service.member.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DefaultToken {
    private String accessToken;
    private String refreshToken;
}
