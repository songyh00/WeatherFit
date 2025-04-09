package com.weatherfit.backend.weather.dto;

import java.util.List;

public class WeatherForecastDto {

    private int minTemperature;                  // 최저 기온
    private int maxTemperature;                  // 최고 기온
    private List<HourlyWeatherDto> hourlyWeather; // 시간별 날씨 데이터 리스트

    // 생성자: 날씨 예보 초기화
    public WeatherForecastDto(int minTemperature, int maxTemperature, List<HourlyWeatherDto> hourlyWeather) {
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
        this.hourlyWeather = hourlyWeather;
    }

    // Getter 메서드들
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
