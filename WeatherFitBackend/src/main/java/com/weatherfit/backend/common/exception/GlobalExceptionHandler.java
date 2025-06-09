package com.weatherfit.backend.common.exception;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.time.LocalDateTime;

/**
 * 전역(Global) 예외 핸들러
 * - 모든 예외를 통합적으로 처리하고 ErrorResponse 포맷으로 응답
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 커스텀 예외 처리
     */
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException ex) {
        ErrorCode errorCode = ex.getErrorCode();
        HttpStatus status = HttpStatus.valueOf(errorCode.getStatus());

        ErrorResponse response = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .error(status.name())
                .code(errorCode.name())
                .message(errorCode.getMessage())
                .build();

        return ResponseEntity.status(status).body(response);
    }

    /**
     * JWT 토큰 만료 예외 처리
     */
    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ErrorResponse> handleExpiredJwtException(ExpiredJwtException ex) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;

        ErrorResponse response = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .error(status.name())
                .code(ErrorCode.TOKEN_EXPIRED.name())
                .message(ErrorCode.TOKEN_EXPIRED.getMessage())
                .build();

        return ResponseEntity.status(status).body(response);
    }

    /**
     * WebClient 예외 처리 (외부 API 실패)
     */
    @ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity<ErrorResponse> handleWebClientException(WebClientResponseException ex) {
        HttpStatus status = HttpStatus.BAD_GATEWAY;

        ErrorResponse response = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .error(status.name())
                .code(ErrorCode.EXTERNAL_API_ERROR.name())
                .message(ErrorCode.EXTERNAL_API_ERROR.getMessage())
                .build();

        return ResponseEntity.status(status).body(response);
    }

    /**
     * 그 외 모든 예외 처리
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        log.error("🔴 서버 내부 오류 발생", ex);

        ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
        HttpStatus status = errorCode.getHttpStatus();

        ErrorResponse response = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .error(status.name())
                .code(errorCode.name())
                .message(errorCode.getMessage())
                .build();

        return ResponseEntity.status(status).body(response);
    }
}
