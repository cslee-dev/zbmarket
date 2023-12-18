package com.example.zbmarket.service.member;

import com.example.zbmarket.exception.ErrorCodeEnum;
import com.example.zbmarket.exception.GlobalException;
import com.example.zbmarket.repository.MemberCartRepository;
import com.example.zbmarket.repository.MemberRepository;
import com.example.zbmarket.repository.entity.MemberCartEntity;
import com.example.zbmarket.repository.entity.MemberEntity;
import com.example.zbmarket.security.util.JwtUtil;
import com.example.zbmarket.service.member.model.DefaultToken;
import com.example.zbmarket.type.member.MemberRoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthMemberService {
    private final MemberRepository memberRepository;
    private final MemberCartRepository memberCartRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberEntity saveMemberEntity(MemberEntity member) throws GlobalException {
        try {
            return memberRepository.save(member);
        } catch (DataIntegrityViolationException e) {
            throw new GlobalException(ErrorCodeEnum.JOINED_MEMBER_ERROR);
        }
    }

    @Transactional
    public DefaultToken createMember(String email, String password) throws GlobalException {
        String encodedPassword = passwordEncoder.encode(password);
        List<String> roles = List.of(MemberRoleEnum.USER.name());
        MemberEntity member = MemberEntity
                .createNewMember(email, encodedPassword, roles);

        MemberEntity saved = saveMemberEntity(member);

        memberCartRepository.save(MemberCartEntity.createNewCart(saved));

        String accessToken = JwtUtil.generateAccessToken(
                saved.getEmail(), MemberRoleEnum.USER.name());
        String refreshToken = JwtUtil.generateRefreshToken(
                saved.getEmail(), MemberRoleEnum.USER.name());

        return DefaultToken.from(accessToken, refreshToken);
    }

}
