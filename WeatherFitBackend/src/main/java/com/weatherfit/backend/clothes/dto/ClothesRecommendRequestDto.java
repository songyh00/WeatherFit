package com.weatherfit.backend.clothes.dto;

import lombok.Data;

/**
 * 사용자가 코디 추천을 요청할 때 보내는 DTO
 */
@Data
public class ClothesRecommendRequestDto {
    private String bannerType; // 배너 타입 (BEST, RECOMMEND, OUTER, TOP, BOTTOM)
    private String address;    // 사용자가 입력한 주소
    private boolean tomorrow;  // 내일 추천 여부 (true: 내일 추천, false: 오늘 추천)

}