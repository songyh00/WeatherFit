package com.weatherfit.backend.weather.controller;

import com.weatherfit.backend.weather.dto.WeatherForecastDto;
import com.weatherfit.backend.weather.service.KakaoAddressService;
import com.weatherfit.backend.weather.service.WeatherService;
import com.weatherfit.backend.weather.util.LocationUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/weather")
public class WeatherController {

    private final KakaoAddressService kakaoAddressService;
    private final WeatherService weatherService;

    public WeatherController(KakaoAddressService kakaoAddressService, WeatherService weatherService) {
        this.kakaoAddressService = kakaoAddressService;
        this.weatherService = weatherService;
    }

    // 날씨 검색 폼 화면을 보여주는 요청 (GET)
    @GetMapping
    public String showWeatherForm() {
        return "weather"; // templates/weather.html 렌더링
    }

    // 주소와 타입을 받아 날씨 데이터 조회 후 화면에 전달
    @GetMapping("/search")
    public String getWeather(@RequestParam String address,
                             @RequestParam String forecastType,
                             Model model) {

        // 1. 주소를 통해 위도/경도 계산
        double[] coordinates = kakaoAddressService.getCoordinates(address);
        double latitude = coordinates[0];
        double longitude = coordinates[1];

        // 2. 위도/경도를 NX/NY 좌표로 변환
        LocationUtil.XY xy = LocationUtil.xyFromLatLng(latitude, longitude);

        // 3. 날씨 정보 조회
        WeatherForecastDto weatherData = weatherService.getForecast(xy.x, xy.y, forecastType);

        // 4. 모델에 날씨 데이터 추가
        model.addAttribute("weatherData", weatherData);
        model.addAttribute("address", address);
        model.addAttribute("forecastType", forecastType);

        return "weather"; // 조회 결과를 포함한 weather.html 렌더링
    }
}
