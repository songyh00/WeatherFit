package com.weatherfit.backend;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherController {
    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/weather")
    public String getWeather(@RequestParam String baseDate,
                             @RequestParam String baseTime,
                             @RequestParam String nx,
                             @RequestParam String ny) {
        return weatherService.getWeather(baseDate, baseTime, nx, ny);

    }
}
