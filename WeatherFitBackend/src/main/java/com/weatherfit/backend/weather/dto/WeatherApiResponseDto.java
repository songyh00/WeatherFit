package com.weatherfit.backend.weather.dto;

import lombok.Data;
import java.util.List;

/**
 * 기상청 단기예보 API 응답을 담는 DTO
 */
@Data
public class WeatherApiResponseDto {

    private Response response; // ⭐ 최상위 응답 객체

    @Data
    public static class Response {
        private Body body; // ⭐ 응답 바디
    }

    @Data
    public static class Body {
        private Items items; // ⭐ 실제 데이터 항목들
    }

    @Data
    public static class Items {
        private List<Item> item; // ⭐ 예보 데이터 리스트
    }

    /**
     * 개별 예보 데이터
     */
    @Data
    public static class Item {
        private String category;   // ⭐ 예보 항목 (TMP, SKY, PCP 등)
        private String fcstDate;   // ⭐ 예보 날짜 (yyyyMMdd)
        private String fcstTime;   // ⭐ 예보 시간 (HHmm)
        private String fcstValue;  // ⭐ 예보 값 (온도, 강수량 등)
    }
}
