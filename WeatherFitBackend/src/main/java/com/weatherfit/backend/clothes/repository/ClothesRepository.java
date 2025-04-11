package com.weatherfit.backend.clothes.repository;

import com.weatherfit.backend.clothes.entity.Clothes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Clothes 엔티티를 위한 JPA 리포지토리
 */
@Repository
public interface ClothesRepository extends JpaRepository<Clothes, Long> {

    /**
     * 특정 카테고리 + 성별 리스트에 해당하는 옷들을 좋아요 순으로 정렬하여 조회
     *
     * @param category 카테고리 (ex: "아우터", "상의", "하의")
     * @param genders  성별 리스트 (ex: ["MALE", "UNISEX"])
     * @return 좋아요 많은 순으로 정렬된 옷 목록
     */
    List<Clothes> findByCategoryAndGenderInOrderByLikeCountDesc(String category, List<String> genders);

    /**
     * 여러 카테고리 + 성별 리스트에 해당하는 옷들을 좋아요 순으로 정렬하여 조회
     *
     * @param categories 카테고리 목록 (ex: ["상의", "원피스"])
     * @param genders    성별 리스트
     * @return 좋아요 많은 순으로 정렬된 옷 목록
     */
    List<Clothes> findByCategoryInAndGenderInOrderByLikeCountDesc(List<String> categories, List<String> genders);

    /**
     * 특정 카테고리 + 성별 리스트에 해당하는 옷들을 조회 (정렬 없음, 랜덤 추출용)
     *
     * @param category 카테고리
     * @param genders  성별 리스트
     * @return 조회된 옷 목록
     */
    List<Clothes> findByCategoryAndGenderIn(String category, List<String> genders);

    /**
     * 여러 카테고리 + 성별 리스트에 해당하는 옷들을 조회 (정렬 없음, 랜덤 추출용)
     *
     * @param categories 카테고리 목록
     * @param genders    성별 리스트
     * @return 조회된 옷 목록
     */
    List<Clothes> findByCategoryInAndGenderIn(List<String> categories, List<String> genders);
}
