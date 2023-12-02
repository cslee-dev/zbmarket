package com.example.zbmarket.security;

import com.example.zbmarket.security.filter.BearerTokenAuthenticationFilter;
import com.example.zbmarket.security.filter.CustomUsernamePasswordAuthenticationFilter;
import com.example.zbmarket.security.handler.CustomAuthenticationSuccessHandler;
import com.example.zbmarket.security.service.CustomUserDetailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig {
    private final AuthenticationConfiguration authenticationConfiguration;
    private final CustomUserDetailService customUserDetailService;
    private final ObjectMapper objectMapper;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Argon2PasswordEncoder();
    }


    public CustomUsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager) throws Exception {
        CustomUsernamePasswordAuthenticationFilter filter =
                new CustomUsernamePasswordAuthenticationFilter(
                        authenticationManager, objectMapper
                );
        filter.setAuthenticationSuccessHandler(new CustomAuthenticationSuccessHandler());
        return filter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .userDetailsService(customUserDetailService)
                .addFilterBefore(usernamePasswordAuthenticationFilter(authenticationConfiguration.getAuthenticationManager()), UsernamePasswordAuthenticationFilter.class) // 로그인 필터
                .csrf().disable()
                .httpBasic().disable()
                .formLogin().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(new BearerTokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class) // 모든 요청에 대해 토큰 검증함
                .authorizeRequests() // 보안규칙 설정
                .antMatchers("/api/v1/join", "/api/v1/sign-in").permitAll() // 인증하지 않음
                .anyRequest().authenticated(); // 그 외 요청은 인증 필요
        return http.build();
    }
}
