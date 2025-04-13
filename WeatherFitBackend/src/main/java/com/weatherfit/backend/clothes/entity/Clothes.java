package com.weatherfit.backend.clothes.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * 옷(Clothes) 정보를 저장하는 엔티티 클래스
 */
@Entity
@Getter
@Setter
@Table(name = "clothes")
public class Clothes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                // 옷 고유 ID (Primary Key)

    private String category;        // 카테고리 ('아우터', '상의', '원피스', '하의')
    private String name;            // 옷 이름
    private String gender;          // 성별 ('MALE', 'FEMALE', 'UNISEX')
    private String imageUrl;        // 옷 이미지 URL

    private int minTemperature;     // 착용 권장 최소 온도
    private int maxTemperature;     // 착용 권장 최대 온도

    private int likeCount;          // 좋아요 수

    /**
     * 좋아요 수 1 증가
     */
    public void increaseLikeCount() {
        this.likeCount++;
    }

    /**
     * 좋아요 수 1 감소
     * (0 미만으로 내려가지 않도록 방어)
     */
    public void decreaseLikeCount() {
        if (this.likeCount > 0) {
            this.likeCount--;
        }
    }
}
