package com.weatherfit.backend.weather.dto;

import java.time.LocalDateTime;

/**
 * 시간별 날씨 정보를 담는 DTO
 */
public class HourlyWeatherDto {

    private final LocalDateTime dateTime;      // 예보 시간
    private final int temperature;             // 기온 (°C)
    private final String weatherDescription;   // 날씨 설명 (맑음, 흐림, 비 등)
    private final String precipitation;        // 강수량(mm) 또는 적설(cm), 없으면 null

    // 생성자
    public HourlyWeatherDto(LocalDateTime dateTime, int temperature, String weatherDescription, String precipitation) {
        this.dateTime = dateTime;
        this.temperature = temperature;
        this.weatherDescription = weatherDescription;
        this.precipitation = precipitation;
    }

    // Getter 메서드
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public int getTemperature() {
        return temperature;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }

    public String getPrecipitation() {
        return precipitation;
    }
}
