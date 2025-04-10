package com.weatherfit.backend.weather.dto;

import java.util.List;

/**
 * 하루 또는 특정 구간의 날씨 예보를 담는 DTO
 */
public class WeatherForecastDto {

    private final int minTemperature;                  // 최저 기온 (°C)
    private final int maxTemperature;                  // 최고 기온 (°C)
    private final int avgTemperature;                  // 평균 기온 (°C)
    private final String mainWeather;                  // 주요 날씨 형태 (맑음, 흐림, 비, 눈)
    private final String precipitationAmount;          // 강수량 (mm)
    private final String snowfallAmount;               // 적설량 (cm)
    private final String precipitationProbability;     // 강수확률 (%)
    private final List<HourlyWeatherDto> hourlyWeather; // 시간별 날씨 데이터 목록

    // 생성자
    public WeatherForecastDto(int minTemperature, int maxTemperature, int avgTemperature,
                              String mainWeather, String precipitationAmount,
                              String snowfallAmount, String precipitationProbability,
                              List<HourlyWeatherDto> hourlyWeather) {
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
        this.avgTemperature = avgTemperature;
        this.mainWeather = mainWeather;
        this.precipitationAmount = precipitationAmount;
        this.snowfallAmount = snowfallAmount;
        this.precipitationProbability = precipitationProbability;
        this.hourlyWeather = hourlyWeather;
    }

    // Getter 메서드
    public int getMinTemperature() {
        return minTemperature;
    }

    public int getMaxTemperature() {
        return maxTemperature;
    }

    public int getAvgTemperature() {
        return avgTemperature;
    }

    public String getMainWeather() {
        return mainWeather;
    }

    public String getPrecipitationAmount() {
        return precipitationAmount;
    }

    public String getSnowfallAmount() {
        return snowfallAmount;
    }

    public String getPrecipitationProbability() {
        return precipitationProbability;
    }

    public List<HourlyWeatherDto> getHourlyWeather() {
        return hourlyWeather;
    }
}
