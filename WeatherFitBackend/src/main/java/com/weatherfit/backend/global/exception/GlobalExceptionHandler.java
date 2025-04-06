package com.weatherfit.backend.global.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

/**
 * 프로젝트 전체에서 발생하는 예외를 처리하는 핸들러
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 모든 예외를 처리하는 메서드
     *
     * @param e 예외 객체
     * @param request 요청 객체 (요청 URI를 알기 위해)
     * @return 예외에 대한 응답
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e, HttpServletRequest request) {
        String path = request.getRequestURI();

        // Swagger 관련 요청이면 에러 무시
        if (path.contains("/v3/api-docs") || path.contains("/swagger-ui")) {
            return ResponseEntity.ok().build();
        }

        // 나머지 요청은 정상적으로 에러 처리
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Internal Server Error: " + e.getMessage());
    }
}
