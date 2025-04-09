package com.weatherfit.backend.weather.dto;

public class HourlyWeatherDto {
    private String forecastTime;
    private double temperature;
    private String weather; // ⭐ 추가: 날씨 상태까지 같이 저장

    public HourlyWeatherDto() {}

    public HourlyWeatherDto(String forecastTime, double temperature, String weather) {
        this.forecastTime = forecastTime;
        this.temperature = temperature;
        this.weather = weather;
    }

    // Getter & Setter
    public String getForecastTime() { return forecastTime; }
    public void setForecastTime(String forecastTime) { this.forecastTime = forecastTime; }
    public double getTemperature() { return temperature; }
    public void setTemperature(double temperature) { this.temperature = temperature; }
    public String getWeather() { return weather; }
    public void setWeather(String weather) { this.weather = weather; }
}
