package com.weatherfit.backend.recommend.controller;

import com.weatherfit.backend.recommend.dto.ClothesDTO;
import com.weatherfit.backend.recommend.service.ClothesService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 추천 API를 제공하는 컨트롤러
 */
@RestController
@RequestMapping("/api/recommend")
@RequiredArgsConstructor
public class RecommendController {

    private final ClothesService clothesService;

    /**
     * [1] 개별 카테고리 추천 (아우터, 상의, 하의)
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

    /**
     * [2] 전체 추천 (아우터, 상의, 하의 각 3장씩 총 9장)
     */
    @GetMapping("/all")
    public Map<String, List<ClothesDTO>> recommendAll(
            @RequestParam @NotNull Double temperature,
            @RequestParam @NotBlank String gender) {

        Map<String, List<ClothesDTO>> result = new LinkedHashMap<>();

        result.put("outer", clothesService.recommendSingleClothes("아우터", temperature, gender)
                .stream()
                .map(c -> new ClothesDTO(c.getCategory(), c.getName(), c.getGender(), c.getImageUrl()))
                .toList());

        result.put("top", clothesService.recommendSingleClothes("상의", temperature, gender)
                .stream()
                .map(c -> new ClothesDTO(c.getCategory(), c.getName(), c.getGender(), c.getImageUrl()))
                .toList());

        result.put("bottom", clothesService.recommendSingleClothes("하의", temperature, gender)
                .stream()
                .map(c -> new ClothesDTO(c.getCategory(), c.getName(), c.getGender(), c.getImageUrl()))
                .toList());

        return result;
    }

    /**
     * [3] 상의 + 하의 추천 (6장)
     */
    @GetMapping("/top-bottom")
    public Map<String, List<ClothesDTO>> recommendTopAndBottom(
            @RequestParam @NotNull Double temperature,
            @RequestParam @NotBlank String gender) {

        Map<String, List<ClothesDTO>> result = new LinkedHashMap<>();

        // 상의 3장 추천
        result.put("top", clothesService.recommendSingleClothes("상의", temperature, gender)
                .stream()
                .map(c -> new ClothesDTO(c.getCategory(), c.getName(), c.getGender(), c.getImageUrl()))
                .toList());

        // 하의 3장 추천
        result.put("bottom", clothesService.recommendSingleClothes("하의", temperature, gender)
                .stream()
                .map(c -> new ClothesDTO(c.getCategory(), c.getName(), c.getGender(), c.getImageUrl()))
                .toList());

        return result;
    }

    /**
     * [4] BEST 추천 (좋아요 많은 옷 추천)
     */
    @GetMapping("/best")
    public Map<String, List<ClothesDTO>> bestClothes(
            @RequestParam @NotNull Double temperature,
            @RequestParam @NotBlank String gender) {

        Map<String, List<ClothesDTO>> result = new LinkedHashMap<>();

        result.put("outer", clothesService.findTop3BestClothes("아우터", temperature, gender)
                .stream()
                .map(c -> new ClothesDTO(c.getCategory(), c.getName(), c.getGender(), c.getImageUrl()))
                .toList());

        result.put("top", clothesService.findTop3BestClothes("상의", temperature, gender)
                .stream()
                .map(c -> new ClothesDTO(c.getCategory(), c.getName(), c.getGender(), c.getImageUrl()))
                .toList());

        result.put("bottom", clothesService.findTop3BestClothes("하의", temperature, gender)
                .stream()
                .map(c -> new ClothesDTO(c.getCategory(), c.getName(), c.getGender(), c.getImageUrl()))
                .toList());

        return result;
    }

    /**
     * [5] 좋아요 클릭 (likeCount 증가)
     */
    @PostMapping("/like")
    public void likeClothes(@RequestParam Long clothesId) {
        clothesService.increaseLikeCount(clothesId);
    }
}
