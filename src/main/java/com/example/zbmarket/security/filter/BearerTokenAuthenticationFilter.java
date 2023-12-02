package com.example.zbmarket.security.filter;

import com.example.zbmarket.security.util.JwtUtil;
import com.example.zbmarket.type.member.MemberRoleEnum;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.log.LogMessage;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.NullSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class BearerTokenAuthenticationFilter extends OncePerRequestFilter {
    private final SecurityContextRepository securityContextRepository = new NullSecurityContextRepository();


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Authentication authenticationResult = attemptAuthentication(request, response);
        if (authenticationResult == null) {
            filterChain.doFilter(request, response);
            return;
        }
        this.successfulAuthentication(request, response, filterChain, authenticationResult);
        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.isAsciiPrintable(bearerToken) && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String token = resolveToken(request);
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        if (!JwtUtil.validateToken(token)) {
            return null;
        }
        return getAuthentication(token);
    }

    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authResult);
        SecurityContextHolder.setContext(context);
        this.securityContextRepository.saveContext(context, request, response);
        if (this.logger.isDebugEnabled()) {
            this.logger.debug(LogMessage.format("Set SecurityContextHolder to %s", authResult));
        }
    }

    public Authentication getAuthentication(String accessToken) {
        //토큰 복호화
        Claims claims = JwtUtil.parseClaims(accessToken);

        if (claims.get("auth") == null) {
            throw new RuntimeException("권한 정보가 없는 토큰 입니다.");
        }
        List<SimpleGrantedAuthority> authorities = List.of(
                new SimpleGrantedAuthority(MemberRoleEnum.USER.name()));
        UserDetails principal = new User(
                claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(
                principal, "", authorities);
    }
}