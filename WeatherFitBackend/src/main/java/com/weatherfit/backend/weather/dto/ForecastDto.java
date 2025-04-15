package com.weatherfit.backend.weather.dto;

import lombok.Builder;
import lombok.Data;
import java.util.List;

/**
 * 옷 추천을 위한 날씨 데이터(Forecast) 정보를 담는 DTO
 */
@Data
@Builder
public class ForecastDto {

    private List<HourlyTemperatureDto> hourlyTemperatures; // 시간별 기온 및 날씨 상태 목록

    private int averageTemperature; // 코디 추천용 평균기온

}
