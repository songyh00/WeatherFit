package com.weatherfit.backend.recommend.controller;

import com.weatherfit.backend.recommend.service.ClothesService;
import com.weatherfit.backend.recommend.dto.ClothesDTO;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * RecommendController
 *
 * - 배너용 (Best, 추천, 아우터, 상의, 하의) 옷 추천 API
 * - 자동 추천용 (주소 검색 후 세션 평균온도 기반 추천) API
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recommend")
public class RecommendController {

    private final ClothesService clothesService;

    /**
     * [배너용] BEST 옷 추천
     * - 성별 + 온도 기반
     * - 좋아요 많은 순서대로 추천
     */
    @GetMapping("/best")
    public List<ClothesDTO> getBestClothes(@RequestParam String gender, @RequestParam int temperature) {
        return clothesService.recommendBest(gender, temperature);
    }

    /**
     * [배너용] 랜덤 옷 추천
     * - 성별 + 온도 기반
     * - 완전 랜덤 추천
     */
    @GetMapping("/random")
    public List<ClothesDTO> getRandomClothes(@RequestParam String gender, @RequestParam int temperature) {
        return clothesService.recommendRandom(gender, temperature);
    }

    /**
     * [배너용] 아우터 추천
     * - 온도 기반
     * - 20도 이하만 추천 (21도 이상이면 메세지 반환)
     */
    @GetMapping("/outer")
    public Object getOuter(@RequestParam int temperature) {
        return clothesService.recommendOuter(temperature);
    }

    /**
     * [배너용] 상의 추천
     * - 성별 기반
     * - 여성일 경우 원피스 포함
     */
    @GetMapping("/top")
    public List<ClothesDTO> getTop(@RequestParam String gender) {
        return clothesService.recommendTop(gender);
    }

    /**
     * [배너용] 하의 추천
     */
    @GetMapping("/bottom")
    public List<ClothesDTO> getBottom() {
        return clothesService.recommendBottom();
    }

    /**
     * [자동 추천용] 주소 검색 후 평균온도 기반 추천
     * - 프론트는 성별만 넘긴다
     * - 평균온도는 세션에서 꺼낸다
     * - 나중에 로그인 시스템 완성되면 성별도 세션 or 로그인 유저에서 가져올 예정
     */
    @GetMapping("/auto")
    public List<ClothesDTO> recommendAuto(@RequestParam String gender, HttpSession session) {
        Integer avgTemperature = (Integer) session.getAttribute("avgTemperature");

        if (avgTemperature == null) {
            throw new IllegalStateException("평균 온도 정보가 없습니다. 먼저 주소를 검색해주세요.");
        }

        return clothesService.recommendBest(gender, avgTemperature);
    }
}
