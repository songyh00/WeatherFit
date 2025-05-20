package com.weatherfit.backend.like.repository;

import com.weatherfit.backend.like.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * 좋아요(Like) 레포지토리
 * - 좋아요 데이터를 DB에서 조회/저장/삭제하는 인터페이스
 */
@Transactional
public interface LikeRepository extends JpaRepository<Like, Long> {

    /**
     * 특정 사용자(userId)가 특정 옷(clothesId)에 누른 좋아요 조회
     */
    Optional<Like> findByUserIdAndClothesId(Long userId, Long clothesId);

    /**
     * 특정 사용자가 누른 모든 좋아요 리스트 조회
     */
    List<Like> findByUserId(Long userId);

    /**
     * 특정 사용자가 누른 좋아요들을 한 번에 삭제 (최적화)
     */
    @Modifying
    @Query("DELETE FROM Likes l WHERE l.userId = :userId")
    void deleteByUserId(Long userId);

}