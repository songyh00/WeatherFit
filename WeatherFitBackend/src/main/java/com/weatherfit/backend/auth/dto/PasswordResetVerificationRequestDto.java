package com.weatherfit.backend.auth.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 비밀번호 재설정 검증 DTO(로그인 전)
 */
@Getter
@Setter
public class PasswordResetVerificationRequestDto {
    private String username;
    private String email;
}