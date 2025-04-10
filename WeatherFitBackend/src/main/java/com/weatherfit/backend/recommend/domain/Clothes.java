package com.weatherfit.backend.recommend.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Clothes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String category; // 아우터, 상의, 하의, 원피스
    private String name;
    private String imageUrl;
    private int likes;
    private String gender; // MALE, FEMALE, UNISEX
}
