package com.weatherfit.backend.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 로그인 응답 DTO
 */
@Getter
@AllArgsConstructor
public class LoginResponseDto {
    private String token;
}
