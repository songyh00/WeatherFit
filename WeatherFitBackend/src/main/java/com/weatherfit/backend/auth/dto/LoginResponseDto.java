package com.weatherfit.backend.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 로그인 응답에 사용되는 DTO
 */
@Getter
@AllArgsConstructor
public class LoginResponseDto {
    private String token;
    private String username;
    private String email;
    private String gender;

}