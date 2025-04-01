package com.weatherfit.backend.recommend;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OutfitRepository extends JpaRepository<Outfit, Long> {
    List<Outfit> findByGenderInAndStyleAndMinTemperatureLessThanEqualAndMaxTemperatureGreaterThanEqual(
            List<String> genders, String style, int minTemperature, int maxTemperature
    );
}