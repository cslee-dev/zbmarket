package com.example.zbmarket.rest.member;

import com.example.zbmarket.rest.member.model.MemberCreateRequestDto;
import com.example.zbmarket.rest.member.model.MemberCreateResponseDto;
import com.example.zbmarket.rest.member.model.MemberMatchRequestDto;
import com.example.zbmarket.rest.member.model.MemberMatchResponseDto;
import com.example.zbmarket.service.member.AuthMemberServiceImpl;
import com.example.zbmarket.service.member.model.DefaultToken;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class MemberController {
    private final AuthMemberServiceImpl authMemberService;

    @PostMapping("/join")
    public MemberCreateResponseDto createMember(
            @RequestBody MemberCreateRequestDto memberCreateRequestDto) {
        DefaultToken created = authMemberService.createMember(
                memberCreateRequestDto.getEmail(),
                memberCreateRequestDto.getPassword()
        );
        return new MemberCreateResponseDto(
                created.getAccessToken(),
                created.getRefreshToken()
        );
    }

    @PostMapping("/sign-in")
    public MemberMatchResponseDto MatchMember(
            @RequestBody MemberMatchRequestDto memberMatchRequestDto
    ) {
        DefaultToken matched = authMemberService.matchMember(
                memberMatchRequestDto.getEmail(),
                memberMatchRequestDto.getPassword()
        );
        return new MemberMatchResponseDto(
                matched.getAccessToken(),
                matched.getRefreshToken()
        );
    }
    
}
