package com.weatherfit.backend.common.enumtype;

import com.weatherfit.backend.common.exception.CustomException;
import com.weatherfit.backend.common.exception.ErrorCode;

/**
 * 사용자 및 옷 성별 구분을 위한 Enum
 */
public enum Gender {
    MALE, FEMALE, UNISEX;

    /**
     * 회원가입 등 사용자 성별 입력 시 사용.
     * UNISEX는 허용되지 않음.
     */
    public static Gender from(String value) {
        try {
            Gender gender = Gender.valueOf(value.toUpperCase());
            if (gender == UNISEX) {
                throw new CustomException(ErrorCode.INVALID_USER_GENDER);
            }
            return gender;
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new CustomException(ErrorCode.BLANK_INPUT_NOT_ALLOWED);
        }
    }
}
