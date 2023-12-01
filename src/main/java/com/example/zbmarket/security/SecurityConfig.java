package com.example.zbmarket.security;

import com.example.zbmarket.security.filter.JwtUsernamePasswordAuthenticationFilter;
import com.example.zbmarket.security.handler.CustomAuthenticationSuccessHandler;
import com.example.zbmarket.security.service.CustomUserDetailService;
import com.example.zbmarket.security.util.JwtUtil;
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
    private final JwtUtil jwtUtil;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Argon2PasswordEncoder();
    }


    public JwtUsernamePasswordAuthenticationFilter authenticationFilter(AuthenticationManager authenticationManager) throws Exception {
        JwtUsernamePasswordAuthenticationFilter filter = new JwtUsernamePasswordAuthenticationFilter(authenticationManager);
        filter.setJwtUtil(jwtUtil);
        filter.setAuthenticationSuccessHandler(new CustomAuthenticationSuccessHandler());
//        filter.setAuthenticationFailureHandler(failureHandler());
        return filter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .formLogin().disable()
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/api/v1/join", "/api/v1/sign-in").permitAll()
                .anyRequest().authenticated()
                .and()
                .userDetailsService(customUserDetailService)
                .addFilterAfter(authenticationFilter(authenticationConfiguration.getAuthenticationManager()), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
