package com.weatherfit.backend.weather.controller;

import com.weatherfit.backend.weather.dto.WeatherForecastDto;
import com.weatherfit.backend.weather.service.KakaoAddressService;
import com.weatherfit.backend.weather.service.WeatherService;
import com.weatherfit.backend.weather.util.LocationUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * WeatherPageTestController
 *
 * - 브라우저에서 HTML로 날씨 조회 + 코디 추천 테스트하는 컨트롤러
 */
@Controller
@RequestMapping("/weather")
public class WeatherPageTestController {

    private final KakaoAddressService kakaoAddressService;
    private final WeatherService weatherService;

    public WeatherPageTestController(KakaoAddressService kakaoAddressService, WeatherService weatherService) {
        this.kakaoAddressService = kakaoAddressService;
        this.weatherService = weatherService;
    }

    /**
     * 처음 접속 (폼만 보여주기)
     */
    @GetMapping
    public String showWeatherForm() {
        return "weather";
    }

    /**
     * 주소, 예보 타입 받아서 날씨 조회하고 세션에 평균기온 저장
     */
    @GetMapping("/search")
    public String searchWeather(@RequestParam String address,
                                @RequestParam String forecastType,
                                Model model,
                                HttpSession session) {

        double[] coordinates = kakaoAddressService.getCoordinates(address);
        double latitude = coordinates[0];
        double longitude = coordinates[1];

        LocationUtil.XY xy = LocationUtil.xyFromLatLng(latitude, longitude);

        WeatherForecastDto weatherData = weatherService.getForecast(xy.x, xy.y, forecastType);

        // 세션에 평균 온도 저장 (코디 추천용)
        session.setAttribute("avgTemperature", weatherData.getAvgTemperature());
        // 세션에 날씨 정보도 저장 (화면 표시용)
        session.setAttribute("weatherData", weatherData);
        session.setAttribute("address", address);
        session.setAttribute("forecastType", forecastType);

        return "redirect:/weather";  // 폼으로 다시 리다이렉트
    }
}
