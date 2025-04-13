package com.weatherfit.backend.clothes.repository;

import com.weatherfit.backend.clothes.entity.Clothes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Clothes 엔티티를 위한 JPA 리포지토리 인터페이스
 */
@Repository
public interface ClothesRepository extends JpaRepository<Clothes, Long> {

    /**
     * 카테고리와 성별 리스트에 해당하는 옷을
     * 좋아요 수 내림차순으로 조회
     *
     * @param category 카테고리 (예: "아우터", "상의", "하의")
     * @param genders  성별 리스트 (예: ["MALE", "UNISEX"])
     * @return 좋아요 수 내림차순 정렬된 옷 목록
     */
    List<Clothes> findByCategoryAndGenderInOrderByLikeCountDesc(String category, List<String> genders);

    /**
     * 여러 카테고리와 성별 리스트에 해당하는 옷을
     * 좋아요 수 내림차순으로 조회
     *
     * @param categories 카테고리 목록 (예: ["상의", "원피스"])
     * @param genders    성별 리스트
     * @return 좋아요 수 내림차순 정렬된 옷 목록
     */
    List<Clothes> findByCategoryInAndGenderInOrderByLikeCountDesc(List<String> categories, List<String> genders);

    /**
     * 카테고리와 성별 리스트에 해당하는 옷을
     * 정렬 없이 조회 (랜덤 추출용)
     *
     * @param category 카테고리
     * @param genders  성별 리스트
     * @return 조회된 옷 목록
     */
    List<Clothes> findByCategoryAndGenderIn(String category, List<String> genders);

    /**
     * 여러 카테고리와 성별 리스트에 해당하는 옷을
     * 정렬 없이 조회 (랜덤 추출용)
     *
     * @param categories 카테고리 목록
     * @param genders    성별 리스트
     * @return 조회된 옷 목록
     */
    List<Clothes> findByCategoryInAndGenderIn(List<String> categories, List<String> genders);
}
