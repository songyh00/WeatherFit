package com.weatherfit.backend.clothes.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * 옷(Clothes) 엔티티
 */
@Entity
@Getter
@Setter
@Table(name = "clothes")
public class Clothes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                // 옷 고유 ID

    private String category;        // 카테고리 ('아우터', '상의', '원피스', '하의')
    private String name;            // 옷 이름
    private String gender;          // 성별 ('MALE', 'FEMALE', 'UNISEX')
    private String imageUrl;        // 이미지 URL
    private int minTemperature;     // 추천 최소 온도
    private int maxTemperature;     // 추천 최대 온도
    private int likeCount;          // 좋아요 수

    /**
     * 좋아요 수 1 증가
     */
    public void increaseLikeCount() {
        this.likeCount++;
    }

    /**
     * 좋아요 수 1 감소 (0 밑으로 내려가지 않게 방어)
     */
    public void decreaseLikeCount() {
        if (this.likeCount > 0) {
            this.likeCount--;
        }
    }
}
