package com.weatherfit.backend.weather.dto;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class ForecastDto {
    private int maxTemperature;    // 최고기온
    private int minTemperature;    // 최저기온
    private List<HourlyTemperatureDto> hourlyTemperatures; // 시간별 온도 리스트
    private String weatherType;    // 현재 날씨 상태 (맑음, 비, 눈, 구름많음 등)
    private double precipitationAmount; // 강수량 (mm)
    private double snowAmount;         // 적설량 (cm)
    private int precipitationProbability; // 강수확률 (%)
    private int averageTemperature; // 평균기온 (09시~00시 기준)
}
