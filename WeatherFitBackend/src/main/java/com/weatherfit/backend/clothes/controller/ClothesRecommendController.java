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

/**
 * [코디 추천용] 사용자가 배너 타입을 선택하고 주소, 날짜를 입력하면
 * 해당 조건에 맞춰 코디를 추천해주는 컨트롤러
 */
@RestController
@RequestMapping("/api/recommend")
@RequiredArgsConstructor
public class ClothesRecommendController {

    private final KakaoAddressService kakaoAddressService; // 주소 → 위도/경도 변환
    private final WeatherService weatherService;           // 날씨 데이터 조회
    private final ClothesService clothesService;           // 코디 추천 서비스
    private final JwtUtil jwtUtil;                         // JWT 토큰 유틸리티

    /**
     * 코디 추천 API
     * @param requestDto 주소, 날짜(오늘/내일), 배너 타입 정보
     * @param request    HttpServletRequest (헤더에서 JWT 토큰 추출용)
     * @return 추천된 옷 리스트
     */
    @PostMapping
    public ClothesRecommendResponseDto recommendClothes(
            @RequestBody ClothesRecommendRequestDto requestDto,
            HttpServletRequest request
    ) {
        // 1. 주소 → 위도/경도 → NX/NY 변환
        double[] coordinates = kakaoAddressService.getCoordinates(requestDto.getAddress());
        double latitude = coordinates[0];
        double longitude = coordinates[1];
        LocationUtil.XY xy = LocationUtil.xyFromLatLng(latitude, longitude);

        // 2. 코디 추천용 날씨 데이터 가져오기
        var weather = weatherService.getForecastForClothing(xy.x, xy.y, requestDto.isTomorrow());

        // 3. JWT 토큰에서 성별 추출
        String token = jwtUtil.cleanTokenFromHeader(request.getHeader("Authorization"));
        String gender = jwtUtil.extractGender(token);

        // 4. 코디 추천 결과 반환
        return clothesService.recommendClothes(
                requestDto,
                weather.getAverageTemperature(),
                gender,
                requestDto.getBannerType(),
                requestDto.isTomorrow()
        );
    }
    
}