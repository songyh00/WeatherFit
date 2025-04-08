package com.weatherfit.backend.recommend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OutfitDTO {
    private String style;
    private String name;
    private String gender;
    private String imageUrl;
}
