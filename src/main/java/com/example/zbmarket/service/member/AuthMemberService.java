package com.example.zbmarket.service.member;

import com.example.zbmarket.service.member.model.DefaultToken;

public interface AuthMemberService {
    DefaultToken createMember(String email, String password);

    DefaultToken matchMember(String email, String password);
}
