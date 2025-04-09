package com.weatherfit.backend.weather.controller;

import com.weatherfit.backend.weather.dto.WeatherForecastDto;
import com.weatherfit.backend.weather.service.KakaoAddressService;
import com.weatherfit.backend.weather.service.WeatherService;
import com.weatherfit.backend.weather.util.LocationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WeatherController {

    @Autowired
    private KakaoAddressService kakaoAddressService;

    @Autowired
    private WeatherService weatherService;

    // 1. 날씨 조회 폼 화면을 보여주는 GET 요청
    @GetMapping("/weather")
    public String showWeatherForm() {
        return "weather"; // templates/weather.html
    }

    // 2. 폼 제출 후 날씨 정보 조회 (주소, forecastType을 받아 처리)
    @GetMapping("/weather/search")
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

        return "weather"; // 조회 결과를 포함한 weather.html 렌더링
    }
}
