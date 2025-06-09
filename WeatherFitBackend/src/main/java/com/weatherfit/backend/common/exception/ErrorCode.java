package com.weatherfit.backend.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * 에러 코드 Enum
 * - 모든 비즈니스 예외 상황을 코드화
 * - HTTP 상태 코드와 사용자 메시지를 함께 포함
 */
@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // =========================
    // 사용자(User) 관련 오류
    // =========================
    USERNAME_ALREADY_EXISTS(HttpStatus.CONFLICT, "이미 존재하는 아이디입니다."),
    EMAIL_ALREADY_EXISTS(HttpStatus.CONFLICT, "이미 존재하는 이메일입니다."),
    INVALID_USER_GENDER(HttpStatus.BAD_REQUEST, "사용자 성별은 남자 또는 여자만 선택 가능합니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 사용자입니다."),
    PASSWORD_MISMATCH(HttpStatus.BAD_REQUEST, "비밀번호가 틀렸습니다."),
    EMAIL_MISMATCHED(HttpStatus.BAD_REQUEST, "이메일이 일치하지 않습니다."),
    EMAIL_SAME_AS_CURRENT(HttpStatus.BAD_REQUEST, "기존 이메일과 동일합니다."),
    NEW_PASSWORD_MISMATCH(HttpStatus.BAD_REQUEST, "새 비밀번호가 일치하지 않습니다."),
    OLD_PASSWORD_REQUIRED(HttpStatus.BAD_REQUEST, "기존 비밀번호가 입력되지 않았습니다."),
    BLANK_INPUT_NOT_ALLOWED(HttpStatus.BAD_REQUEST, "공백은 허용하지 않습니다."),

    // =========================
    // 인증(Token) 관련 오류
    // =========================
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다."),

    // =========================
    // 옷(Clothes) 관련 오류
    // =========================
    CLOTHES_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 옷입니다."),

    // =========================
    // 외부 API 통신 관련 오류
    // =========================
    EXTERNAL_API_ERROR(HttpStatus.BAD_GATEWAY, "외부 API 호출 중 오류가 발생했습니다."),

    // =========================
    // 기타 비즈니스 로직 오류
    // =========================
    INVALID_BANNER_TYPE(HttpStatus.BAD_REQUEST, "유효하지 않은 배너 타입입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류가 발생했습니다.");

    private final HttpStatus status;
    private final String message;

    public int getStatus() {
        return status.value();
    }

    public HttpStatus getHttpStatus() {
        return status;
    }
}
