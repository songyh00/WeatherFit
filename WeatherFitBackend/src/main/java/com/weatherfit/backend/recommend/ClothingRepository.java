package com.weatherfit.backend.recommend;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClothingRepository extends JpaRepository<Clothing, Long> {
    List<Clothing> findByCategoryAndMinTemperatureLessThanEqualAndMaxTemperatureGreaterThanEqualAndWeatherTypeIn(
            Clothing.Category category,
            int temp1,
            int temp2,
            List<Clothing.WeatherType> weatherTypes
    );
}
