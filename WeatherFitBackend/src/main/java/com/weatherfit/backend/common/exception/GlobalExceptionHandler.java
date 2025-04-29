package com.weatherfit.backend.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import io.jsonwebtoken.ExpiredJwtException;

/**
 * 전역 예외 처리 핸들러
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
        log.warn("❌ CustomException 발생: {}", errorCode.getMessage());
        return new ResponseEntity<>(new ErrorResponse(errorCode.name(), errorCode.getMessage()), HttpStatus.BAD_REQUEST);
    }

    /**
     * JWT 만료 예외 처리
     */
    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ErrorResponse> handleExpiredJwtException(ExpiredJwtException ex) {
        log.warn("⏳ JWT 만료됨: {}", ex.getMessage());
        return new ResponseEntity<>(new ErrorResponse("EXPIRED_TOKEN", "토큰이 만료되었습니다."), HttpStatus.UNAUTHORIZED);
    }

    /**
     * WebClient 응답 오류 처리 (4xx, 5xx 응답을 받은 경우)
     */
    @ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity<ErrorResponse> handleWebClientResponseException(WebClientResponseException ex) {
        log.error("🌐 외부 API 응답 오류: status={}, body={}", ex.getStatusCode(), ex.getResponseBodyAsString());
        return new ResponseEntity<>(new ErrorResponse("EXTERNAL_API_RESPONSE_ERROR", "외부 API에서 오류 응답이 도착했습니다."),
                HttpStatus.BAD_GATEWAY);
    }

    /**
     * 예상치 못한 서버 오류 처리
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        log.error("🔥 서버 내부 오류 발생", ex);
        return new ResponseEntity<>(new ErrorResponse("INTERNAL_SERVER_ERROR", "서버 오류가 발생했습니다."),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 에러 응답 포맷
     */
    public record ErrorResponse(String code, String message) {}
}
