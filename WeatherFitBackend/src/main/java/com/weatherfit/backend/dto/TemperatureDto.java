package com.weatherfit.backend.dto;

public class TemperatureDto {

    private String temperature;
    private String time;
    private String date;

    public TemperatureDto(){}

    public TemperatureDto(String temperature, String time, String date) {
        this.temperature = temperature;
        this.time = time;
        this.date = date;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
