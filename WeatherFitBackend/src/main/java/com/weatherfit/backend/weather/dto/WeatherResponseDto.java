package com.weatherfit.backend.weather.dto;

import lombok.Builder;
import lombok.Data;
import java.util.List;

/**
 * 날씨 예보를 위한 날씨 데이터(WeatherResponse) 정보를 담는 DTO
 */
@Data
@Builder
public class WeatherResponseDto {
    private List<HourlyTemperatureDto> hourlyTemperatures;   // 시간별 기온 및 날씨 상태 목록
    private int minTemperature;                              //날씨 예보용 최소온도
    private int maxTemperature;                              //날씨 예보용 최고온도

}