package com.weatherfit.backend.auth.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 아이디 찾기 DTO
 */
@Getter
@Setter
public class FindUsernameRequestDto {
    private String email;
    private String password;
}