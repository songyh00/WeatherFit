package com.weatherfit.backend.auth.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 비밀번호 재설정 DTO(로그인 전)
 */
@Getter
@Setter
public class PasswordResetRequestDto {
    private String username;
    private String newPassword;
    private String newPasswordConfirm;
}