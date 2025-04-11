package com.weatherfit.backend.clothes.controller;

import com.weatherfit.backend.weather.service.KakaoAddressService;
import com.weatherfit.backend.weather.service.WeatherService;
import com.weatherfit.backend.clothes.dto.ClothesRecommendRequestDto;
import com.weatherfit.backend.clothes.dto.ClothesRecommendResponseDto;
import com.weatherfit.backend.clothes.service.ClothesService;
import com.weatherfit.backend.auth.JwtUtil;
import com.weatherfit.backend.weather.util.LocationUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recommend")
@RequiredArgsConstructor
public class ClothesRecommendController {

    private final KakaoAddressService kakaoAddressService;
    private final WeatherService weatherService;
    private final ClothesService clothesService;
    private final JwtUtil jwtUtil;

    @PostMapping
    public ClothesRecommendResponseDto recommendClothes(
            @RequestBody ClothesRecommendRequestDto requestDto, // ✅ @RequestBody로 통째로 받는다
            HttpServletRequest request
    ) {
        // 1. 주소 → 위도/경도 → NX/NY 변환
        double[] coordinates = kakaoAddressService.getCoordinates(requestDto.getAddress());
        double latitude = coordinates[0];
        double longitude = coordinates[1];
        LocationUtil.XY xy = LocationUtil.xyFromLatLng(latitude, longitude);

        // 2. 날씨 데이터 가져오기
        var weather = weatherService.getForecast(xy.x, xy.y, requestDto.isTomorrow());

        // 3. JWT 토큰에서 성별 가져오기
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Authorization 헤더가 없습니다.");
        }
        String token = authorizationHeader.substring(7);
        String gender = jwtUtil.extractGender(token);

        // 4. 추천 결과 반환
        return clothesService.recommendClothes(
                requestDto,
                weather.getAverageTemperature(),
                weather.getMaxTemperature(),
                weather.getWeatherType(),
                gender,
                requestDto.getBannerType()
        );
    }
}
