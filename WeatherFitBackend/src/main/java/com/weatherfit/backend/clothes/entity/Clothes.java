package com.weatherfit.backend.clothes.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "clothes")
public class Clothes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String category;    // '아우터', '상의', '원피스', '하의'
    private String name;        // 옷 이름
    private String gender;      // 'MALE', 'FEMALE', 'UNISEX'
    private String imageUrl;    // 이미지 URL
    private int minTemperature; // 추천 최소 온도
    private int maxTemperature; // 추천 최대 온도
    private int likeCount;      // 좋아요 수

    // ⭐ 좋아요 수 증가
    public void increaseLikeCount() {
        this.likeCount++;
    }

    // ⭐ 좋아요 수 감소 (0 밑으로 안 내려가게)
    public void decreaseLikeCount() {
        if (this.likeCount > 0) {
            this.likeCount--;
        }
    }
}
