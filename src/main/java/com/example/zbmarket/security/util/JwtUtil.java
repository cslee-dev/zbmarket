package com.example.zbmarket.security.util;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String secretKey;
    @Value("${jwt.expire-date}")
    private Long expireDate;
    @Value("${jwt.refresh.expire-date}")
    private Long refreshExpireDate;

    private SecretKey getKey() {
        byte[] encodeKey = Base64.getDecoder().decode(secretKey);
        return Keys.hmacShaKeyFor(encodeKey);
    }

    public String generateAccessToken(String email, String auth) {
        return Jwts.builder()
                .subject(email)
                .claim("auth", auth)
                .expiration(new Date(System.currentTimeMillis() + expireDate))
                .signWith(getKey()).compact();
    }

    public String generateRefreshToken(String email, String auth) {
        return Jwts.builder()
                .subject(email)
                .claim("auth", auth)
                .expiration(new Date(
                        System.currentTimeMillis() + refreshExpireDate))
                .signWith(getKey()).compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException |
                 MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty.", e);
        }
        return false;
    }
}
