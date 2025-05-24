package com.weatherfit.backend.common.exception;

import lombok.Getter;

/**
 * 커스텀 예외 클래스
 * - 모든 비즈니스 예외는 이 클래스를 통해 처리
 */
@Getter
public class CustomException extends RuntimeException {

    private final ErrorCode errorCode;

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}