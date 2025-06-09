package com.weatherfit.backend.weather.controller;

import com.weatherfit.backend.weather.dto.WeatherResponseDto;
import com.weatherfit.backend.weather.service.KakaoAddressService;
import com.weatherfit.backend.weather.service.WeatherService;
import com.weatherfit.backend.weather.util.LocationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 날씨 정보를 제공하는 컨트롤러(날씨 예보용)
 */
@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    @Autowired
    private KakaoAddressService kakaoAddressService;

    @Autowired
    private WeatherService weatherService;

    /**
     * 주소 기준으로 현재 시각부터 내일 23시까지의 날씨 데이터를 조회하는 API
     */
    @GetMapping
    public WeatherResponseDto getWeather(@RequestParam String address) {
        double[] coordinates = kakaoAddressService.getCoordinates(address);
        LocationUtil.XY xy = LocationUtil.xyFromLatLng(coordinates[0], coordinates[1]);
        return weatherService.getForecastFromNowToTomorrowNight(xy.x, xy.y);
    }
}
