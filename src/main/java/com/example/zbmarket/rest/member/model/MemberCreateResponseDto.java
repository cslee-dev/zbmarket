package com.example.zbmarket.rest.member.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MemberCreateResponseDto {
    private String accessToken;
    private String refreshToken;
}
