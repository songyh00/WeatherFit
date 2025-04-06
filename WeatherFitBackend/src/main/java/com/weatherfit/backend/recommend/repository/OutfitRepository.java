package com.weatherfit.backend.recommend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.weatherfit.backend.recommend.domain.Outfit;


import java.util.List;

/**
 * 코디(Outfit) 관련 DB 조회 Repository
 */
public interface OutfitRepository extends JpaRepository<Outfit, Long> {

    @Query("SELECT o FROM Outfit o " +
            "WHERE o.minTemperature <= :temperature AND o.maxTemperature >= :temperature " +
            "AND o.style = :style " +
            "AND (o.gender = :gender OR o.gender = 'UNISEX')")
    List<Outfit> findOutfitByTemperatureAndStyleAndGender(
            @Param("temperature") double temperature,
            @Param("style") String style,
            @Param("gender") String gender
    );

    @Query("SELECT o FROM Outfit o " +
            "WHERE o.minTemperature <= :temperature AND o.maxTemperature >= :temperature " +
            "AND (o.gender = :gender OR o.gender = 'UNISEX')")
    List<Outfit> findOutfitByTemperatureAndGender(
            @Param("temperature") double temperature,
            @Param("gender") String gender
    );
}
