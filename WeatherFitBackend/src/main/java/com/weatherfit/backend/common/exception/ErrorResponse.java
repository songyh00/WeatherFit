package com.weatherfit.backend.common.exception;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 에러 응답용 DTO
 * - API 에러 발생 시 통일된 응답 포맷 제공
 */
@Getter
@Builder
public class ErrorResponse {
    private LocalDateTime timestamp; // 에러 발생 시간
    private int status;              // HTTP 상태 코드 (400, 401 등)
    private String error;            // HTTP 상태명 (BAD_REQUEST 등)
    private String code;             // 커스텀 에러 코드 (ErrorCode Enum 이름)
    private String message;          // 에러 상세 메시지

}