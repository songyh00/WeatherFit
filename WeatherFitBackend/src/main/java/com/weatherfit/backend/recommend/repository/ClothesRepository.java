package com.weatherfit.backend.recommend.repository;

import com.weatherfit.backend.recommend.domain.Clothes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClothesRepository extends JpaRepository<Clothes, Long> {

    @Query("SELECT c FROM Clothes c WHERE c.gender IN (:gender, 'UNISEX') ORDER BY c.likes DESC")
    List<Clothes> findBestClothesByLikes(String gender);

    @Query("SELECT c FROM Clothes c WHERE c.gender IN (:gender, 'UNISEX')")
    List<Clothes> findAllRandom(String gender);

    List<Clothes> findByCategory(String category);
}
