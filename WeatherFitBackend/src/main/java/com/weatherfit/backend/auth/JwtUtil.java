package com.weatherfit.backend.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Base64;
import java.util.Date;

/**
 * JWT 토큰 생성 및 파싱을 담당하는 유틸리티 클래스
 */
@Component
public class JwtUtil {

    private final SecretKey secretKey;

    // JWT 만료 시간: 1일 (24시간)
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24;

    /**
     * 생성자 - Base64로 인코딩된 secret key를 디코딩하여 SecretKey 생성
     * @param secret application.properties에 설정된 secret-key
     */
    public JwtUtil(@Value("${jwt.secret-key}") String secret) {
        byte[] decodedKey = Base64.getDecoder().decode(secret);
        this.secretKey = Keys.hmacShaKeyFor(decodedKey);
    }

    /**
     * JWT 토큰 생성
     * @param id 사용자 ID
     * @param gender 사용자 성별
     * @return 생성된 JWT 토큰 문자열
     */
    public String generateToken(Long id, String gender) {
        return Jwts.builder()
                .claim("id", id)             // 사용자 ID 클레임 추가
                .claim("gender", gender)      // 사용자 성별 클레임 추가
                .setIssuedAt(new Date())      // 발급 시간
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // 만료 시간
                .signWith(secretKey, SignatureAlgorithm.HS256) // 서명
                .compact();
    }

    /**
     * 토큰에서 사용자 성별(gender) 추출
     * @param token JWT 토큰
     * @return 사용자 성별
     */
    public String extractGender(String token) {
        Claims claims = parseToken(token);
        return claims.get("gender", String.class);
    }

    /**
     * 토큰에서 사용자 ID 추출
     * @param token JWT 토큰
     * @return 사용자 ID
     */
    public Long extractUserId(String token) {
        Claims claims = parseToken(token);
        return claims.get("id", Long.class);
    }

    /**
     * 토큰을 파싱하여 Claims 추출 (공통 부분 메서드화)
     * @param token JWT 토큰
     * @return Claims
     */
    private Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
