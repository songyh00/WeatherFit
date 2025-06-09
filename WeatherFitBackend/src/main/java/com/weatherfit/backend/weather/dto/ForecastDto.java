package com.weatherfit.backend.weather.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * 옷 추천을 위한 날씨 데이터(Forecast) 정보를 담는 DTO
 */
@ToString
@Data
@Builder
public class ForecastDto {
    private List<HourlyTemperatureDto> hourlyTemperatures;
    private int averageTemperature;
}