package com.weatherfit.backend.weather.dto;

import lombok.Data;
import java.util.List;

@Data
public class KakaoApiResponseDto {

    private List<Document> documents;

    @Data
    public static class Document {
        private String x; // 경도 (Longitude)
        private String y; // 위도 (Latitude)
    }
}
