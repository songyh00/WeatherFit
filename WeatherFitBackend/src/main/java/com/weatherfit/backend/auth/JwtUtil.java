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

    public JwtUtil(@Value("${jwt.secret-key}") String secret) {
        byte[] decodedKey = Base64.getDecoder().decode(secret); // ⭐ Base64 디코딩
        this.secretKey = Keys.hmacShaKeyFor(decodedKey);         // ⭐ 안전한 SecretKey 생성
    }

    public String generateToken(Long id, String gender) {
        return Jwts.builder()
                .claim("id", id)               // 사용자 ID 넣기
                .claim("gender", gender)       // 사용자 성별 넣기
                .setIssuedAt(new Date())        // 발급 시간
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // 만료 시간
                .signWith(secretKey, SignatureAlgorithm.HS256) // ⭐ 키와 알고리즘 설정
                .compact();
    }

    public String extractGender(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)       // ⭐ 키 설정
                .build()
                .parseClaimsJws(token)           // 토큰 파싱
                .getBody();

        return claims.get("gender", String.class); // gender 클레임 꺼내기
    }

    public Long extractUserId(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.get("id", Long.class);
    }


}
