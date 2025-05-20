package com.weatherfit.backend.clothes.repository;

import com.weatherfit.backend.clothes.entity.Clothes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Clothes 엔티티를 위한 JPA 리포지토리 인터페이스
 */
@Repository
public interface ClothesRepository extends JpaRepository<Clothes, Long> {

    /**
     * 단일 카테고리와 성별 리스트에 해당하는 옷을 좋아요 수 내림차순으로 조회
     */
    List<Clothes> findByCategoryAndGenderInOrderByLikeCountDesc(String category, List<String> genders);

    /**
     * 여러 카테고리(상의, 원피스)와 성별 리스트에 해당하는 옷을 좋아요 수 내림차순으로 조회
     */
    List<Clothes> findByCategoryInAndGenderInOrderByLikeCountDesc(List<String> categories, List<String> genders);

    /**
     * 단일 카테고리와 성별 리스트에 해당하는 옷을 정렬 없이 조회 (랜덤 추출용)
     */
    List<Clothes> findByCategoryAndGenderIn(String category, List<String> genders);

    /**
     * 여러 카테고리(상의, 원피스)와 성별 리스트에 해당하는 옷을 정렬 없이 조회 (랜덤 추출용)
     */
    List<Clothes> findByCategoryInAndGenderIn(List<String> categories, List<String> genders);

    /**
     * 특정 사용자가 좋아요 누른 옷들의 likeCount를 한 번에 감소시키는 쿼리
     * - 'Like' 엔티티가 예약어라 충돌 방지 위해 @Entity(name="Likes") 지정했음
     */
    @Modifying
    @Query("UPDATE Clothes c SET c.likeCount = c.likeCount - 1 " +
            "WHERE c.id IN (SELECT l.clothes.id FROM Likes l WHERE l.userId = :userId)")
    void decreaseLikeCountByUserId(Long userId);

}