package com.weatherfit.backend.clothes.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

/**
 * 코디 추천 결과를 담아 반환하는 DTO (Response DTO)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClothesRecommendResponseDto {

    private List<RecommendedClothesDto> recommendedClothes; // 추천된 옷 목록

    /**
     * 추천된 개별 옷 정보를 나타내는 내부 DTO
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class RecommendedClothesDto {
        private Long id;           // 옷 ID
        private String imageUrl;   // 옷 이미지 URL
        private int likeCount;     // 좋아요 수
    }
}
