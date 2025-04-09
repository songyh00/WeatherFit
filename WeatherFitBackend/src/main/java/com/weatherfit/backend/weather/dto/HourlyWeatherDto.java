package com.weatherfit.backend.weather.dto;

import java.time.LocalDateTime;

public class HourlyWeatherDto {

    private LocalDateTime dateTime;      // 날씨 정보가 측정된 시간
    private int temperature;             // 측정된 온도 (섭씨)
    private String weatherDescription;   // 날씨 설명 (예: 맑음, 흐림, 비, 눈 등)
    private String precipitation;        // 강수량(mm) 또는 적설(cm), 없으면 null

    // 생성자: 날씨 정보 초기화
    public HourlyWeatherDto(LocalDateTime dateTime, int temperature, String weatherDescription, String precipitation) {
        this.dateTime = dateTime;
        this.temperature = temperature;
        this.weatherDescription = weatherDescription;
        this.precipitation = precipitation;
    }

    // Getter 메서드들
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
