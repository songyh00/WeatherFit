package com.weatherfit.backend.weather.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TemperatureDto {
    private int minTemperature; // 최저 기온
    private int maxTemperature; // 최고 기온
}
