package com.weatherfit.backend.weather.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HourlyTemperatureDto {
    private String forecastTime;  // 예: "1400"
    private int temperature;      // 예: 18도
}
