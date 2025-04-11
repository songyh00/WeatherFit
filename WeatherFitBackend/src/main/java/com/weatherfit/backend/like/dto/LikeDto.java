package com.weatherfit.backend.like.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LikeDto {
    private Long id;         // 옷 id
    private String imageUrl; // 옷 이미지
    private String name;     // 옷 이름
    private String category; // 옷 카테고리
}
