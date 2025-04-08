package com.weatherfit.backend.weather;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/weather")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;

    @GetMapping("/get")
    public String getWeather(
            @RequestParam(name = "baseDate") String baseDate,
            @RequestParam(name = "baseTime") String baseTime,
            @RequestParam(name = "nx") String nx,
            @RequestParam(name = "ny") String ny
    ) {
        return weatherService.getWeather(baseDate, baseTime, nx, ny);
    }
}
