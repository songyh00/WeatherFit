package com.weatherfit.backend.auth.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 회원가입 요청 시 사용되는 DTO
 * username(아이디), password(비밀번호), email(이메일), gender(성별)을 담는다.
 */
@Getter
@Setter
public class SignupRequestDto {
    private String username; // 사용자 아이디
    private String password; // 사용자 비밀번호
    private String email;    // 사용자 이메일
    private String gender;   // 사용자 성별 (예: MALE, FEMALE)
}
