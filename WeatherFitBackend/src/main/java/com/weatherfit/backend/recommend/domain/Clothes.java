package com.weatherfit.backend.recommend.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 옷(Clothes) 엔티티
 * - 각 옷에 대한 기본 정보와 좋아요 수를 저장
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Clothes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 옷 ID

    @Column(nullable = false)
    private String category; // 카테고리 (상의, 하의, 아우터)

    @Column(nullable = false)
    private String name; // 옷 이름

    @Column(nullable = false)
    private String gender; // 성별 (MALE, FEMALE, UNISEX)

    @Column(name = "image_url", nullable = false)
    private String imageUrl; // 옷 이미지 링크

    @Column(name = "min_temperature", nullable = false)
    private int minTemperature; // 입을 수 있는 최저 기온

    @Column(name = "max_temperature", nullable = false)
    private int maxTemperature; // 입을 수 있는 최고 기온

    @Column(nullable = false)
    private Integer likeCount = 0; // 좋아요 수 (기본값 0)
}
