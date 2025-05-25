package com.weatherfit.backend.like.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 마이페이지에서 좋아요한 옷 정보를 나타내는 DTO
 */
@Getter
@Setter
@AllArgsConstructor
public class LikeDto {
    private Long id;          // 옷 ID (Primary Key)
    private String imageUrl;  // 옷 이미지 URL
    private String name;      // 옷 이름
    private String category;  // 옷 카테고리 (아우터, 상의, 하의, 원피스)
}