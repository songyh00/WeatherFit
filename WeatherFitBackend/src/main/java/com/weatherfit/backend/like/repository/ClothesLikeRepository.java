package com.weatherfit.backend.like.repository;

import com.weatherfit.backend.like.domain.ClothesLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface ClothesLikeRepository extends JpaRepository<ClothesLike, Long> {

    Optional<ClothesLike> findByUsersIdAndClothesId(Long usersId, Long clothesId);

    List<ClothesLike> findByUsersId(Long usersId);
}
