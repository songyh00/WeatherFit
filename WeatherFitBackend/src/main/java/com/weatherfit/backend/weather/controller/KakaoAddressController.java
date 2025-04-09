package com.weatherfit.backend.weather.controller;

import com.weatherfit.backend.weather.dto.WeatherForecastDto;
import com.weatherfit.backend.weather.service.KakaoAddressService;
import com.weatherfit.backend.weather.service.WeatherService;
import com.weatherfit.backend.weather.util.LocationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/address")
public class KakaoAddressController {

    @Autowired
    private KakaoAddressService kakaoAddressService;

    @Autowired
    private WeatherService weatherService;

    @GetMapping("/search")
    public WeatherForecastDto getWeatherByAddress(@RequestParam String address,
                                                  @RequestParam(defaultValue = "false") boolean tomorrow) {
        // 1. 주소 → 위도/경도
        double[] coordinates = kakaoAddressService.getCoordinates(address);
        double latitude = coordinates[0];
        double longitude = coordinates[1];

        // 2. 위도/경도 → NX/NY 변환
        LocationUtil.XY xy = LocationUtil.xyFromLatLng(latitude, longitude);

        // 3. NX/NY → 날씨 정보 조회
        return weatherService.getForecast(xy.x, xy.y, tomorrow);
    }
}
