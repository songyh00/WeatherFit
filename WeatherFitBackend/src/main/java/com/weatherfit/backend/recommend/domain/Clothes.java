package com.weatherfit.backend.recommend.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Clothes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category", nullable = false)
    private String category;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "gender", nullable = false)
    private String gender;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(name = "min_temperature", nullable = false)
    private int minTemperature;

    @Column(name = "max_temperature", nullable = false)
    private int maxTemperature;
}
