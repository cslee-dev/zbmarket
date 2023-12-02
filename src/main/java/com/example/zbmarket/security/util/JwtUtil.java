package com.example.zbmarket.security.util;

import io.jsonwebtoken.*;
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
    private static String secretKey;
    private static Long expireDate;
    private static Long refreshExpireDate;


    private static SecretKey getKey() {
        byte[] encodeKey = Base64.getDecoder().decode(secretKey);
        return Keys.hmacShaKeyFor(encodeKey);
    }

    public static String generateAccessToken(String email, String auth) {
        return Jwts.builder()
                .subject(email)
                .claim("auth", auth)
                .expiration(new Date(System.currentTimeMillis() + expireDate))
                .signWith(getKey()).compact();
    }

    public static String generateRefreshToken(String email, String auth) {
        return Jwts.builder()
                .subject(email)
                .claim("auth", auth)
                .expiration(new Date(
                        System.currentTimeMillis() + refreshExpireDate))
                .signWith(getKey()).compact();
    }

    public static boolean validateToken(String token) {
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

    @Value("${jwt.secret}")
    public void setSecretKey(String secretKey) {
        JwtUtil.secretKey = secretKey;
    }

    @Value("${jwt.expire-date}")
    public void setExpireDate(Long expireDate) {
        JwtUtil.expireDate = expireDate;
    }

    @Value("${jwt.refresh.expire-date}")
    public void setRefreshExpireDate(Long refreshExpireDate) {
        JwtUtil.refreshExpireDate = refreshExpireDate;
    }

    public static Claims parseClaims(String accessToken) {
        try {
            return Jwts.parser().verifyWith(getKey()).build()
                    .parseSignedClaims(accessToken).getPayload();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}
