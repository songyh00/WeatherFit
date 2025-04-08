package com.weatherfit.backend.apicontroller;

import com.weatherfit.backend.apiservice.WeatherService;
import com.weatherfit.backend.dto.TemperatureDto;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/weather") // API 기본 경로
@CrossOrigin(origins = "*")
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/temperature")
    public TemperatureDto getTemperature(
            @RequestParam(name = "baseDate") String baseDate,
            @RequestParam(name = "baseTime") String baseTime,
            @RequestParam(name = "nx") String nx,
            @RequestParam(name = "ny") String ny
    ) {
        return weatherService.getTemperature(baseDate, baseTime, nx, ny);
    }

}
