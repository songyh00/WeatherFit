package com.weatherfit.backend.weather.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HourlyTemperatureDto {   // ← 클래스명도 HourlyTemperatureDto로!
    private String time;
    private int temperature;
}
