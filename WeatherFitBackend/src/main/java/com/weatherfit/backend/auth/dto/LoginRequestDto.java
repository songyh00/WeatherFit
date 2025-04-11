package com.weatherfit.backend.auth.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 로그인 요청 시 사용되는 DTO
 * username(아이디)와 password(비밀번호)를 담는다.
 */
@Getter
@Setter
public class LoginRequestDto {
    private String username; // 사용자 아이디
    private String password; // 사용자 비밀번호
}
