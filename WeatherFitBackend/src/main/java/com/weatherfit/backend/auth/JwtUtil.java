package com.weatherfit.backend.auth;

import com.weatherfit.backend.common.exception.CustomException;
import com.weatherfit.backend.common.exception.ErrorCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

/**
 * JWT 토큰 생성 및 파싱을 담당하는 유틸리티 클래스
 */
@Component
public class JwtUtil {

    private final SecretKey secretKey;

    /**
     * JWT 만료 시간: 1일 (24시간)
     */
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24;

    public JwtUtil(@Value("${jwt.secret-key}") String secret) {
        byte[] decodedKey = Base64.getDecoder().decode(secret);
        this.secretKey = Keys.hmacShaKeyFor(decodedKey);
    }

    /**
     * JWT 토큰 생성
     */
    public String generateToken(Long id) {
        return Jwts.builder()
                .claim("id", id)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Authorization 헤더에서 Bearer 제거 및 토큰 추출
     */
    public String cleanTokenFromHeader(String header) {
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        throw new CustomException(ErrorCode.TOKEN_EXPIRED);
    }

    /**
     * JWT 토큰을 파싱하여 Claims 추출
     */
    private Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 토큰에서 사용자 ID 추출
     */
    public Long extractUserId(String token) {
        Claims claims = parseToken(cleanToken(token));
        return claims.get("id", Long.class);
    }

    /**
     * Bearer 접두어 제거 (내부용)
     */
    private String cleanToken(String token) {
        return token != null && token.startsWith("Bearer ") ? token.substring(7) : token;
    }
}