package com.weatherfit.backend.recommend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ClothesDTO {
    private String category;
    private String name;
    private String gender;
    private String imageUrl;
}
