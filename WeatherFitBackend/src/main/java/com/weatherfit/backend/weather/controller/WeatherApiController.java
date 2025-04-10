package com.weatherfit.backend.weather.controller;

import com.weatherfit.backend.weather.dto.WeatherForecastDto;
import com.weatherfit.backend.weather.service.KakaoAddressService;
import com.weatherfit.backend.weather.service.WeatherService;
import com.weatherfit.backend.weather.util.LocationUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

/**
 * WeatherApiController
 *
 * - JSON 형식으로 날씨 정보를 제공하는 API 컨트롤러
 * - 프론트엔드(React 등)나 Postman으로 데이터 호출할 때 사용
 */
@RestController
@RequestMapping("/api/weather")
public class WeatherApiController {

    private final KakaoAddressService kakaoAddressService;
    private final WeatherService weatherService;

    // 생성자 주입
    public WeatherApiController(KakaoAddressService kakaoAddressService, WeatherService weatherService) {
        this.kakaoAddressService = kakaoAddressService;
        this.weatherService = weatherService;
    }

    /**
     * 주소와 예보 타입(today, tomorrow)을 받아 날씨 정보를 조회하고 JSON으로 반환한다.
     * 동시에 평균 기온을 세션에 저장한다.
     *
     * @param address 검색할 주소
     * @param forecastType 예보 타입 ("today" 또는 "tomorrow")
     * @param session HTTP 세션 객체 (평균 기온을 저장하기 위해 사용)
     * @return WeatherForecastDto (최저/최고/평균 기온, 시간별 날씨 데이터 포함)
     */
    @GetMapping("/search")
    public WeatherForecastDto getWeather(@RequestParam String address,
                                         @RequestParam String forecastType,
                                         HttpSession session) {

        // 1. 주소 → 위도/경도 변환
        double[] coordinates = kakaoAddressService.getCoordinates(address);
        double latitude = coordinates[0];
        double longitude = coordinates[1];

        // 2. 위도/경도 → 기상청 격자 좌표 변환
        LocationUtil.XY xy = LocationUtil.xyFromLatLng(latitude, longitude);

        // 3. 날씨 정보 조회
        WeatherForecastDto weatherForecast = weatherService.getForecast(xy.x, xy.y, forecastType);

        // 4. 평균 기온을 세션에 저장
        session.setAttribute("avgTemperature", weatherForecast.getAvgTemperature());

        return weatherForecast;
    }
}
