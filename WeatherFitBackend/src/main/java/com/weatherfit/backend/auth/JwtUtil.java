package com.weatherfit.backend.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {

    private final SecretKey secretKey;
    private final long EXPIRATION_TIME = 1000 * 60 * 60 * 24; // 1일 (24시간)

    /**
     * 생성자 - Base64로 인코딩된 secret key를 받아 안전한 SecretKey 생성
     */
    public JwtUtil(@Value("${jwt.secret-key}") String secret) {
        byte[] decodedKey = Base64.getDecoder().decode(secret);  // Base64 디코딩
        this.secretKey = Keys.hmacShaKeyFor(decodedKey);         // SecretKey 생성
    }

    /**
     * JWT 토큰 생성
     * @param id 사용자 ID
     * @param gender 사용자 성별
     * @return 생성된 JWT 토큰 문자열
     */
    public String generateToken(Long id, String gender) {
        return Jwts.builder()
                .claim("id", id)                     // 사용자 ID 클레임
                .claim("gender", gender)             // 사용자 성별 클레임
                .setIssuedAt(new Date())             // 발급 시간
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // 만료 시간
                .signWith(secretKey, SignatureAlgorithm.HS256) // 서명
                .compact();
    }

    /**
     * 토큰에서 gender(성별) 추출
     * @param token JWT 토큰
     * @return 사용자 성별
     */
    public String extractGender(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.get("gender", String.class);
    }

    /**
     * 토큰에서 userId 추출
     * @param token JWT 토큰
     * @return 사용자 ID
     */
    public Long extractUserId(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.get("id", Long.class);
    }
}
