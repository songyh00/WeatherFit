package com.weatherfit.backend.weather.controller;

import com.weatherfit.backend.weather.dto.DailyTemperatureDto;
import com.weatherfit.backend.weather.dto.HourlyTemperatureDto;
import com.weatherfit.backend.weather.service.WeatherService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/weather") // API 기본 경로
@CrossOrigin(origins = "*")
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    // ✅ 하루 최대/최소 온도 조회
    @GetMapping("/daily-temperature")
    public DailyTemperatureDto getDailyTemperature(
            @RequestParam(name = "baseDate") String baseDate,
            @RequestParam(name = "baseTime") String baseTime,
            @RequestParam(name = "nx") String nx,
            @RequestParam(name = "ny") String ny
    ) {
        return weatherService.getDailyTemperature(baseDate, baseTime, nx, ny);
    }

    // ✅ 시간대별 온도 조회
    @GetMapping("/hourly-temperature")
    public List<HourlyTemperatureDto> getHourlyTemperature(
            @RequestParam(name = "baseDate") String baseDate,
            @RequestParam(name = "baseTime") String baseTime,
            @RequestParam(name = "nx") String nx,
            @RequestParam(name = "ny") String ny
    ) {
        return weatherService.getHourlyTemperature(baseDate, baseTime, nx, ny);
    }
}
