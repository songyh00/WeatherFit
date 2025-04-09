package com.weatherfit.backend.weather.dto;

import java.util.List;

public class WeatherForecastDto {
    private String date;
    private double minTemperature;
    private double maxTemperature;
    private List<HourlyWeatherDto> weatherList; // ⭐ 온도+날씨 합친 리스트 하나만

    // Getter & Setter
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public double getMinTemperature() { return minTemperature; }
    public void setMinTemperature(double minTemperature) { this.minTemperature = minTemperature; }
    public double getMaxTemperature() { return maxTemperature; }
    public void setMaxTemperature(double maxTemperature) { this.maxTemperature = maxTemperature; }
    public List<HourlyWeatherDto> getWeatherList() { return weatherList; }
    public void setWeatherList(List<HourlyWeatherDto> weatherList) { this.weatherList = weatherList; }
}
