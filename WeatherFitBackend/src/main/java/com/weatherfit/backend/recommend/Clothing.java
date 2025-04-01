package com.weatherfit.backend.recommend;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Clothing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Category category;

    private String imageUrl;
    private Integer minTemperature;
    private Integer maxTemperature;

    @Enumerated(EnumType.STRING)
    private WeatherType weatherType;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String style;
    private LocalDateTime createdAt = LocalDateTime.now();

    public enum Category { OUTER, TOP, BOTTOM }
    public enum WeatherType { SUNNY, CLOUDY, RAINY, SNOWY, ANY }
    public enum Gender { MALE, FEMALE, UNISEX }

    // Getters & Setters
    public Long getId() { return id; }
    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public Integer getMinTemperature() { return minTemperature; }
    public void setMinTemperature(Integer minTemperature) { this.minTemperature = minTemperature; }
    public Integer getMaxTemperature() { return maxTemperature; }
    public void setMaxTemperature(Integer maxTemperature) { this.maxTemperature = maxTemperature; }
    public WeatherType getWeatherType() { return weatherType; }
    public void setWeatherType(WeatherType weatherType) { this.weatherType = weatherType; }
    public Gender getGender() { return gender; }
    public void setGender(Gender gender) { this.gender = gender; }
    public String getStyle() { return style; }
    public void setStyle(String style) { this.style = style; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
