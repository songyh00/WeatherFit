package com.weatherfit.backend.weather.dto;

import lombok.Builder;
import lombok.Data;
import java.util.List;

/**
 * 날씨 정보를 담는 DTO
 */
@Data
@Builder
public class ForecastDto {

    private int maxTemperature;             // ⭐ 최고 기온
    private int minTemperature;             // ⭐ 최저 기온
    private List<HourlyTemperatureDto> hourlyTemperatures; // ⭐ 시간별 기온 목록

    private String weatherType;             // ⭐ 현재 날씨 상태 (맑음, 비, 눈, 구름많음 등)
    private double precipitationAmount;     // ⭐ 강수량 (mm)
    private double snowAmount;               // ⭐ 적설량 (cm)
    private int precipitationProbability;   // ⭐ 강수 확률 (%)

    private int averageTemperature;         // ⭐ 평균 기온 (09시 ~ 00시 기준)
}
