package com.weatherfit.backend.recommend.controller;

import com.weatherfit.backend.recommend.dto.ClothesDTO;
import com.weatherfit.backend.recommend.dto.OutfitDTO;
import com.weatherfit.backend.recommend.service.ClothesService;
import com.weatherfit.backend.recommend.service.OutfitService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 추천 관련 API 컨트롤러
 */
@RestController
@RequestMapping("/api/recommend")
@RequiredArgsConstructor
public class RecommendController {

    private final OutfitService outfitService;
    private final ClothesService clothesService;

    /**
     * [1] 스타일 기반 전체 코디 추천
     * 스타일 선택 안 하면 전체 스타일 추천
     */
    @GetMapping("/outfit")
    public List<OutfitDTO> recommendOutfitSet(
            @RequestParam @NotNull Double temperature,
            @RequestParam(required = false) String style,   // ★ 스타일은 선택사항
            @RequestParam @NotBlank String gender) {

        return outfitService.recommendOutfitSet(temperature, style, gender)
                .stream()
                .map(o -> new OutfitDTO(o.getStyle(), o.getName(), o.getGender(), o.getImageUrl()))
                .toList();
    }

    /**
     * [2] 카테고리별 개별 옷 추천
     */
    @GetMapping("/single")
    public List<ClothesDTO> recommendSingleClothes(
            @RequestParam @NotBlank String category,
            @RequestParam @NotNull Double temperature,
            @RequestParam @NotBlank String gender) {

        return clothesService.recommendSingleClothes(category, temperature, gender)
                .stream()
                .map(c -> new ClothesDTO(c.getCategory(), c.getName(), c.getGender(), c.getImageUrl()))
                .toList();
    }
}
