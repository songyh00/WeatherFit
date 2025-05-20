package com.weatherfit.backend.user.dto;

import com.weatherfit.backend.common.enumtype.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 사용자 프로필 조회 응답 DTO
 */
@Getter
@AllArgsConstructor
public class UserProfileResponseDto {
    private String username;
    private String email;
    private Gender gender;
}
