package com.weatherfit.backend.weather;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/weather") // API 기본 경로
@CrossOrigin(origins = "*")
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/get")
    public String getWeather(
            @RequestParam(name = "baseDate") String baseDate,
            @RequestParam(name = "baseTime") String baseTime,
            @RequestParam(name = "nx") String nx,
            @RequestParam(name = "ny") String ny
    ) {
        return weatherService.getWeather(baseDate, baseTime, nx, ny);
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }
}
