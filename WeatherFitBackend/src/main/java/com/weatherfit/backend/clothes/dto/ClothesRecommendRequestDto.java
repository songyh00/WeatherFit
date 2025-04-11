package com.weatherfit.backend.clothes.dto;

import lombok.Data;

/**
 * 사용자가 코디 추천을 요청할 때 보내는 요청 데이터
 */
@Data
public class ClothesRecommendRequestDto {

    private String bannerType; // 추천 받을 배너 타입 (BEST, RECOMMEND, OUTER, TOP, BOTTOM)
    private String address;    // 사용자가 입력한 주소
    private boolean tomorrow;  // 내일 추천 여부 (true: 내일, false: 오늘)
}
