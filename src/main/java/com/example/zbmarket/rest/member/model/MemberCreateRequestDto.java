package com.example.zbmarket.rest.member.model;

import lombok.Data;

@Data
public class MemberCreateRequestDto {
    private String email;
    private String password;
}
