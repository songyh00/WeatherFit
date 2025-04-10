package com.weatherfit.backend.weather.dto;

import java.util.List;

/**
 * 하루 또는 특정 구간의 날씨 예보를 담는 DTO
 */
public class WeatherForecastDto {

    private final int minTemperature;                  // 최저 기온 (°C)
    private final int maxTemperature;                  // 최고 기온 (°C)
    private final List<HourlyWeatherDto> hourlyWeather; // 시간별 날씨 데이터 목록

    // 생성자
    public WeatherForecastDto(int minTemperature, int maxTemperature, List<HourlyWeatherDto> hourlyWeather) {
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
        this.hourlyWeather = hourlyWeather;
    }

    // Getter 메서드
    public int getMinTemperature() {
        return minTemperature;
    }

    public int getMaxTemperature() {
        return maxTemperature;
    }

    public List<HourlyWeatherDto> getHourlyWeather() {
        return hourlyWeather;
    }
}
