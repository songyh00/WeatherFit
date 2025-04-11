package com.weatherfit.backend.like.repository;

import com.weatherfit.backend.like.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Like(좋아요) 레포지토리
 * 좋아요 데이터를 DB에서 조회/저장/삭제하는 인터페이스
 */
@Transactional
public interface LikeRepository extends JpaRepository<Like, Long> {

    /**
     * 특정 사용자(userId)가 특정 옷(clothesId)에 누른 좋아요 조회
     * (있으면 Optional로 반환)
     */
    Optional<Like> findByUserIdAndClothesId(Long userId, Long clothesId);

    /**
     * 특정 사용자가 누른 모든 좋아요 리스트 조회
     */
    List<Like> findByUserId(Long userId);

    /**
     * 특정 사용자가 누른 모든 좋아요 삭제
     */
    void deleteByUserId(Long userId);
}
