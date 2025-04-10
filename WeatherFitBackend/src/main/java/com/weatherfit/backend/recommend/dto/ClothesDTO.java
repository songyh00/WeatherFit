package com.weatherfit.backend.recommend.dto;

import com.weatherfit.backend.recommend.domain.Clothes;
import lombok.Getter;

@Getter
public class ClothesDTO {

    private final Long id;
    private final String category;
    private final String name;
    private final String imageUrl;
    private final int likes;

    public ClothesDTO(Clothes clothes) {
        this.id = clothes.getId();
        this.category = clothes.getCategory();
        this.name = clothes.getName();
        this.imageUrl = clothes.getImageUrl();
        this.likes = clothes.getLikes();
    }
}
