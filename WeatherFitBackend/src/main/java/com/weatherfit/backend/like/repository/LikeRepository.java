package com.weatherfit.backend.like.repository;

import com.weatherfit.backend.like.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByUserIdAndClothesId(Long userId, Long clothesId);
    List<Like> findByUserId(Long userId);
    void deleteByUserId(Long userId);
}
