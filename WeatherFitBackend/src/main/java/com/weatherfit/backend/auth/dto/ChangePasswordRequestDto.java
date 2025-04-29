package com.weatherfit.backend.auth.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 로그인 후 비밀번호 변경 요청을 위한 DTO
 */
@Getter
@Setter
public class ChangePasswordRequestDto {
    private String oldPassword; // 현 비밀번호
    private String newPassword; // 새 비밀번호

}