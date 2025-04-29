package com.weatherfit.backend.auth.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 회원가입 요청을 위한 DTO
 */
@Getter
@Setter
public class SignupRequestDto {
    private String username;
    private String password;
    private String email;
    private String gender;

}