package com.weatherfit.backend.clothes.entity;

import com.weatherfit.backend.common.enumtype.Gender;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * 옷 엔티티
 */
@Entity
@Getter
@Setter
@Table(name = "clothes")
public class Clothes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category")
    private String category;

    @Column(name = "name")
    private String name;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "image_url")
    private String image;

    @Column(name = "min_temperature")
    private int minTemperature;

    @Column(name = "max_temperature")
    private int maxTemperature;

    @Column(name = "like_count")
    private int likeCount;

    public void increaseLikeCount() {
        this.likeCount++;
    }

    public void decreaseLikeCount() {
        if (this.likeCount > 0) {
            this.likeCount--;
        }
    }

}