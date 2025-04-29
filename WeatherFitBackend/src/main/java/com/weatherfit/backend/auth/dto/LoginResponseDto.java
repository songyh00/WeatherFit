package com.weatherfit.backend.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 로그인 응답에 사용되는 DTO
 * - JWT 토큰과 사용자명을 함께 반환
 */
@Getter
@AllArgsConstructor
public class LoginResponseDto {
    private String token;
    private String username;
}
