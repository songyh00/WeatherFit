package com.weatherfit.backend.recommend.controller;

import com.weatherfit.backend.recommend.dto.ClothesDTO;
import com.weatherfit.backend.recommend.service.ClothesService;
import com.weatherfit.backend.weather.dto.WeatherForecastDto;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * RecommendPageTestController
 *
 * - 브라우저 테스트용 (HTML) 코디 추천 컨트롤러
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/recommend")
public class RecommendPageTestController {

    private final ClothesService clothesService;

    @GetMapping("/auto")
    public String recommendAuto(@RequestParam String gender, HttpSession session, Model model) {
        Integer avgTemperature = (Integer) session.getAttribute("avgTemperature");
        WeatherForecastDto weatherData = (WeatherForecastDto) session.getAttribute("weatherData");

        if (avgTemperature == null || weatherData == null) {
            throw new IllegalStateException("날씨 정보를 먼저 조회해주세요.");
        }

        // 5개 배너 - Best, Random, Outer, Top, Bottom 각각 가져오기
        List<ClothesDTO> bestClothes = clothesService.recommendBest(gender, avgTemperature);
        List<ClothesDTO> randomClothes = clothesService.recommendRandom(gender, avgTemperature);
        Object outerClothes = clothesService.recommendOuter(avgTemperature);
        List<ClothesDTO> topClothes = clothesService.recommendTop(gender);
        List<ClothesDTO> bottomClothes = clothesService.recommendBottom();

        model.addAttribute("weatherData", weatherData);
        model.addAttribute("bestClothes", bestClothes);
        model.addAttribute("randomClothes", randomClothes);
        model.addAttribute("outerClothes", outerClothes);
        model.addAttribute("topClothes", topClothes);
        model.addAttribute("bottomClothes", bottomClothes);
        model.addAttribute("address", session.getAttribute("address"));
        model.addAttribute("forecastType", session.getAttribute("forecastType"));

        return "weather"; // 👉 결과를 weather.html에 뿌려줌
    }
}
