package com.weatherfit.backend.weather.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 시간별 온도 정보를 담는 DTO
 */
@Data
@AllArgsConstructor
public class HourlyTemperatureDto { // ⭐ 시간별 온도 DTO

    private String time;        // ⭐ 시간 (예: "0900", "1200")
    private int temperature;    // ⭐ 해당 시간의 기온 (°C)

}
