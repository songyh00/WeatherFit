package com.weatherfit.backend.weather.controller;

import com.weatherfit.backend.weather.service.WeatherService;
import com.weatherfit.backend.weather.dto.TemperatureDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/weather")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class WeatherController {

    private final WeatherService weatherService;

    @GetMapping("/temperature")
    public TemperatureDto getTemperature(
            @RequestParam String baseDate,
            @RequestParam String baseTime,
            @RequestParam String nx,
            @RequestParam String ny
    ) {
        return weatherService.getTemperature(baseDate, baseTime, nx, ny);
    }
}
