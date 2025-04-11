package com.weatherfit.backend.weather.controller;

import com.weatherfit.backend.weather.dto.ForecastDto;
import com.weatherfit.backend.weather.service.KakaoAddressService;
import com.weatherfit.backend.weather.service.WeatherService;
import com.weatherfit.backend.weather.util.LocationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    @Autowired
    private KakaoAddressService kakaoAddressService; // 주소 → 위경도 변환

    @Autowired
    private WeatherService weatherService; // 날씨 데이터 가져오기

    /**
     * 주소와 날짜를 기준으로 날씨 데이터를 반환하는 API
     *
     * @param address   조회할 주소
     * @param tomorrow  오늘(false) / 내일(true) 선택
     * @return ForecastDto (날씨 정보)
     */
    @GetMapping
    public ForecastDto getWeather(@RequestParam String address,
                                  @RequestParam(defaultValue = "false") boolean tomorrow) {
        // 1. 주소를 위도/경도로 변환
        double[] coordinates = kakaoAddressService.getCoordinates(address);
        double latitude = coordinates[0];
        double longitude = coordinates[1];

        // 2. 위경도를 nx, ny 격자 좌표로 변환
        LocationUtil.XY xy = LocationUtil.xyFromLatLng(latitude, longitude);

        // 3. nx, ny를 기준으로 날씨 데이터 가져오기
        return weatherService.getForecast(xy.x, xy.y, tomorrow);
    }
}
