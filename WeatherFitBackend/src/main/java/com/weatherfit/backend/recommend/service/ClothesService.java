package com.weatherfit.backend.recommend.service;

import com.weatherfit.backend.recommend.domain.Clothes;
import com.weatherfit.backend.recommend.repository.ClothesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;

/**
 * 옷 추천, 좋아요 관리 서비스
 */
@Service
@RequiredArgsConstructor
public class ClothesService {

    private final ClothesRepository clothesRepository;

    /**
     * 개별 카테고리(상의, 하의, 아우터) 추천
     * - 현재 온도와 성별에 맞는 옷을 랜덤으로 3개 추천
     */
    public List<Clothes> recommendSingleClothes(String category, double temperature, String gender) {
        List<Clothes> clothes = clothesRepository.findClothesByCategoryAndTemperatureAndGender(category, temperature, gender);

        if (clothes.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "추천 결과가 없습니다.");
        }

        Collections.shuffle(clothes); // 무작위 섞기
        return clothes.stream()
                .limit(3)
                .toList();
    }

    /**
     * 좋아요 수 증가
     * - 사용자가 특정 옷에 좋아요 버튼을 누르면 호출
     */
    public void increaseLikeCount(Long clothesId) {
        Clothes clothes = clothesRepository.findById(clothesId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "옷을 찾을 수 없습니다."));
        clothes.setLikeCount(clothes.getLikeCount() + 1);
        clothesRepository.save(clothes);
    }

    /**
     * BEST 추천
     * - 온도/성별 조건을 만족하고
     * - 좋아요 수가 많은 옷을 랜덤 섞은 뒤 다시 좋아요 순으로 정렬
     */
    public List<Clothes> findTop3BestClothes(String category, double temperature, String gender) {
        List<Clothes> clothesList = clothesRepository.findBestClothesByCategoryAndTemperatureAndGender(category, temperature, gender);

        if (clothesList.size() > 1) {
            Collections.shuffle(clothesList); // 동률 랜덤 섞기
            clothesList = clothesList.stream()
                    .sorted((c1, c2) -> Integer.compare(c2.getLikeCount(), c1.getLikeCount()))
                    .toList();
        }

        return clothesList.stream()
                .limit(3)
                .toList();
    }
}
