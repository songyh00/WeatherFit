package com.weatherfit.backend.clothes.dto;

import lombok.Data;

@Data
public class ClothesRecommendRequestDto {
    private String bannerType; // BEST, RECOMMEND, OUTER, TOP, BOTTOM
    private String address;
    private boolean tomorrow;
}
