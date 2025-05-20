package com.weatherfit.backend.clothes.controller;

import com.weatherfit.backend.auth.JwtUtil;
import com.weatherfit.backend.clothes.dto.ClothesRecommendRequestDto;
import com.weatherfit.backend.clothes.dto.ClothesRecommendResponseDto;
import com.weatherfit.backend.clothes.service.ClothesService;
import com.weatherfit.backend.user.entity.User;
import com.weatherfit.backend.user.repository.UserRepository;
import com.weatherfit.backend.weather.service.KakaoAddressService;
import com.weatherfit.backend.weather.service.WeatherService;
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
    private final UserRepository userRepository;

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

        // 3. JWT에서 userId 추출 후, DB에서 최신 사용자 정보 조회
        String token = jwtUtil.cleanTokenFromHeader(request.getHeader("Authorization"));
        Long userId = jwtUtil.extractUserId(token);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다.")); // 예외는 필요 시 CustomException으로 변경

        String gender = user.getGender().name();

        // 4. 코디 추천
        return clothesService.recommendClothes(
                requestDto,
                weather.getAverageTemperature(),
                gender,
                requestDto.getBannerType(),
                requestDto.isTomorrow()
        );
    }
}
