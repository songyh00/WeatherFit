package com.weatherfit.backend.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    USER_NOT_FOUND("존재하지 않는 사용자입니다."),
    PASSWORD_MISMATCH("비밀번호가 틀렸습니다."),
    USERNAME_ALREADY_EXISTS("이미 존재하는 아이디입니다."),
    EMAIL_ALREADY_EXISTS("이미 존재하는 이메일입니다."),
    INVALID_TOKEN("유효하지 않은 토큰입니다."),
    CLOTHES_NOT_FOUND("존재하지 않는 옷입니다."),
    EMAIL_NOT_MATCHED("이메일이 일치하지 않습니다."),
    INVALID_USER_GENDER("사용자 성별은 남자 또는 여자만 선택 가능합니다."),
    EXTERNAL_API_ERROR("외부 API 호출 중 오류가 발생했습니다."),
    INVALID_BANNER_TYPE("유효하지 않은 배너 타입입니다.");

    private final String message;
}
