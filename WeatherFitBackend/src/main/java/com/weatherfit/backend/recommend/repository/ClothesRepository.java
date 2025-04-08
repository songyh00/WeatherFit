package com.weatherfit.backend.recommend.repository;

import com.weatherfit.backend.recommend.domain.Clothes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 옷(Clothes) 관련 DB 조회 Repository
 */
public interface ClothesRepository extends JpaRepository<Clothes, Long> {

    // 추천용: 온도, 성별 조건에 맞는 옷 목록 조회
    @Query("SELECT c FROM Clothes c " +
            "WHERE c.minTemperature <= :temperature AND c.maxTemperature >= :temperature " +
            "AND c.category = :category " +
            "AND (c.gender = :gender OR c.gender = 'UNISEX')")
    List<Clothes> findClothesByCategoryAndTemperatureAndGender(
            @Param("category") String category,
            @Param("temperature") double temperature,
            @Param("gender") String gender
    );

    // BEST 추천용: 온도, 성별 조건에 맞고 좋아요 수가 많은 옷 목록 조회
    @Query("SELECT c FROM Clothes c " +
            "WHERE c.minTemperature <= :temperature AND c.maxTemperature >= :temperature " +
            "AND c.category = :category " +
            "AND (c.gender = :gender OR c.gender = 'UNISEX') " +
            "ORDER BY c.likeCount DESC")
    List<Clothes> findBestClothesByCategoryAndTemperatureAndGender(
            @Param("category") String category,
            @Param("temperature") double temperature,
            @Param("gender") String gender
    );
}
