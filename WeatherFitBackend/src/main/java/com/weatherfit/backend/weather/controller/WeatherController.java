package com.weatherfit.backend.weather.controller;

import com.weatherfit.backend.weather.dto.WeatherForecastDto;
import com.weatherfit.backend.weather.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping("/forecast")
    public WeatherForecastDto getForecast(@RequestParam int nx, @RequestParam int ny,
                                          @RequestParam(defaultValue = "false") boolean tomorrow) {
        return weatherService.getForecast(nx, ny, tomorrow);
    }
}
