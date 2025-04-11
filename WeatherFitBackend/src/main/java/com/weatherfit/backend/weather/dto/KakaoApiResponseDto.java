package com.weatherfit.backend.weather.dto;

import lombok.Data;
import java.util.List;

/**
 * 카카오맵 주소 검색 API 응답을 담는 DTO
 */
@Data
public class KakaoApiResponseDto {

    private List<Document> documents; // ⭐ 검색 결과 문서 리스트

    /**
     * 개별 주소 정보 (위도/경도)
     */
    @Data
    public static class Document {
        private String x; // ⭐ 경도 (Longitude)
        private String y; // ⭐ 위도 (Latitude)
    }
}
