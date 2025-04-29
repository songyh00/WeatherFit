package com.weatherfit.backend.weather.dto;

import lombok.Data;
import java.util.List;

/**
 * 카카오맵 주소 검색 API 응답을 담는 DTO
 */
@Data
public class KakaoApiResponseDto {

    private List<Document> documents; // 검색 결과 문서 리스트

    /**
     * 개별 주소 결과를 담는 내부 클래스
     * - 위도/경도 좌표 정보를 포함
     */
    @Data
    public static class Document {
        private String x; // 경도 (longitude)
        private String y; // 위도 (latitude)
    }

}