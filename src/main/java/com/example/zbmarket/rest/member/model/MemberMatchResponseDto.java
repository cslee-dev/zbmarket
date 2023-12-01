package com.example.zbmarket.rest.member.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MemberMatchResponseDto {
    private String accessToken;
    private String refreshToken;
}
