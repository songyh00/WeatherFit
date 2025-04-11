package com.weatherfit.backend.clothes.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClothesRecommendResponseDto {

    private List<RecommendedClothesDto> recommendedClothes;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class RecommendedClothesDto {
        private Long id;           // 옷 ID
        private String imageUrl;   // 옷 이미지 URL
        private int likeCount;     // 현재 좋아요 수
    }
}
