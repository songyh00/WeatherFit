package com.weatherfit.backend.recommend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 클라이언트에게 전달할 옷 정보 DTO
 */
@Getter
@AllArgsConstructor
public class ClothesDTO {

    private String category;
    private String name;
    private String gender;
    private String imageUrl;
}
