package com.weatherfit.backend.clothes.repository;

import com.weatherfit.backend.clothes.entity.Clothes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClothesRepository extends JpaRepository<Clothes, Long> {

    // 1. 특정 카테고리 + 성별 리스트에 해당하는 옷들을 likeCount 내림차순 정렬
    List<Clothes> findByCategoryAndGenderInOrderByLikeCountDesc(String category, List<String> genders);

    // 2. 특정 카테고리 목록(상의 + 원피스 등) + 성별 리스트에 해당하는 옷들을 likeCount 내림차순 정렬
    List<Clothes> findByCategoryInAndGenderInOrderByLikeCountDesc(List<String> categories, List<String> genders);

    // 3. 특정 카테고리 + 성별 리스트에 해당하는 옷들을 랜덤으로 가져오기
    List<Clothes> findByCategoryAndGenderIn(String category, List<String> genders);

    // 4. 특정 카테고리 목록 + 성별 리스트에 해당하는 옷들을 랜덤으로 가져오기
    List<Clothes> findByCategoryInAndGenderIn(List<String> categories, List<String> genders);
}
