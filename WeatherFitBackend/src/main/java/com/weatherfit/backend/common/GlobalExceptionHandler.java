package com.weatherfit.backend.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 전역 예외 처리 핸들러
 * - 애플리케이션 전역에서 발생하는 예외를 잡아 처리한다.
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 모든 RuntimeException 처리
     * @param ex 발생한 런타임 예외
     * @return 에러 응답 (HTTP 400 Bad Request)
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleRuntimeException(RuntimeException ex) {
        log.error("🔴 예외 발생: {}", ex.getMessage()); // 서버에 에러 로그 출력

        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("timestamp", LocalDateTime.now().toString()); // 에러 발생 시간
        errorResponse.put("status", HttpStatus.BAD_REQUEST.value());    // HTTP 상태 코드
        errorResponse.put("error", "Bad Request");                      // 에러 설명
        errorResponse.put("message", ex.getMessage());                  // 예외 메시지
        errorResponse.put("path", ""); // TODO: 추후 요청 경로 추가 가능 (ex: request.getRequestURI())

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }
}
