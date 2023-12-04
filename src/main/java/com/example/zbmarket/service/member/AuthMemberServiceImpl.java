package com.example.zbmarket.service.member;

import com.example.zbmarket.repository.MemberCartRepository;
import com.example.zbmarket.repository.MemberRepository;
import com.example.zbmarket.repository.entity.MemberCartEntity;
import com.example.zbmarket.repository.entity.MemberEntity;
import com.example.zbmarket.security.util.JwtUtil;
import com.example.zbmarket.service.member.model.DefaultToken;
import com.example.zbmarket.type.member.MemberRoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthMemberServiceImpl implements AuthMemberService {
    private final MemberRepository memberRepository;
    private final MemberCartRepository memberCartRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public DefaultToken createMember(String email, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        LocalDateTime now = LocalDateTime.now();
        List<String> roles = List.of(MemberRoleEnum.USER.name());
        MemberEntity saved = memberRepository.save(
                MemberEntity.builder()
                        .id(null)
                        .email(email)
                        .password(encodedPassword)
                        .createdAt(now)
                        .updatedAt(now)
                        .roles(roles)
                        .build()
        );

        memberCartRepository.save(
                MemberCartEntity.builder()
                        .member(saved)
                        .createdAt(now)
                        .updatedAt(now)
                        .build()
        );
        String accessToken = JwtUtil.generateAccessToken(
                saved.getEmail(), MemberRoleEnum.USER.name());
        String refreshToken = JwtUtil.generateRefreshToken(
                saved.getEmail(), MemberRoleEnum.USER.name());
        return DefaultToken.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

}
