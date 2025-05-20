package com.weatherfit.backend.user.dto;

import com.weatherfit.backend.common.enumtype.Gender;
import lombok.Getter;
import lombok.Setter;

/**
 * 사용자 프로필 수정 요청 DTO
 */
@Getter
@Setter
public class UpdateProfileRequestDto {
    private String email;
    private Gender gender;
}