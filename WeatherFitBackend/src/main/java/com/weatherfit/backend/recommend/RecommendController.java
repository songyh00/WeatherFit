package com.weatherfit.backend.recommend;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class RecommendController {

    private final ClothingService clothingService;

    public RecommendController(ClothingService clothingService) {
        this.clothingService = clothingService;
    }

    @GetMapping("/recommend")
    public ResponseEntity<Map<String, List<String>>> recommend(
            @RequestParam int temperature,
            @RequestParam String weatherType,
            @RequestParam String gender // 추가됨: MALE / FEMALE
    ) {
        try {
            Clothing.WeatherType type = Clothing.WeatherType.valueOf(weatherType.toUpperCase());
            Clothing.Gender userGender = Clothing.Gender.valueOf(gender.toUpperCase());

            Map<String, List<String>> recommended = clothingService.recommendOutfits(temperature, type, userGender);
            return ResponseEntity.ok(recommended);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
