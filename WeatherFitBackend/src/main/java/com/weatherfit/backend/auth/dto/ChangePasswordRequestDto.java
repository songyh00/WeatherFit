package com.weatherfit.backend.auth.dto;

import lombok.Getter;

/**
 * 로그인 후 비밀번호 변경 요청을 위한 DTO
 */
@Getter
public class ChangePasswordRequestDto {
    private String oldPassword;
    private String newPassword;
}
