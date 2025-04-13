package com.weatherfit.backend.weather.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 시간별 기온 및 날씨 정보를 담는 DTO
 */
@Data
@AllArgsConstructor
public class HourlyTemperatureDto {

    private String month;        // 월 (예: "04")
    private String day;          // 일 (예: "15")
    private String time;         // 시간 (예: "0900")

    private int temperature;                // 현재 기온 (°C)
    private String weatherType;             // 날씨 상태 (맑음, 비, 눈, 구름많음 등)
    private int precipitationProbability;   // 강수 확률 (%)
    private double precipitationAmount;     // 강수량 (mm)
    private double snowAmount;               // 적설량 (cm)
}
