package com.weatherfit.backend.user.dto;

import lombok.Getter;

/**
 * 사용자 프로필 수정 요청 DTO
 */
@Getter
public class UpdateProfileRequestDto {
    private String newEmail;   // 새 이메일
    private String newGender;  // 새 성별 (MALE/FEMALE)

}