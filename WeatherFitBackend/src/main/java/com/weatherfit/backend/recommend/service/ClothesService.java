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
 * 개별 옷 추천 관련 서비스
 */
@Service
@RequiredArgsConstructor
public class ClothesService {

    private final ClothesRepository clothesRepository;

    public List<Clothes> recommendSingleClothes(String category, double temperature, String gender) {
        List<Clothes> clothes = clothesRepository.findClothesByCategoryAndTemperatureAndGender(category, temperature, gender);

        if (clothes.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "추천 결과가 없습니다.");
        }

        Collections.shuffle(clothes); // 리스트 랜덤 섞기
        return clothes.stream()
                .limit(3) // 3개만 추출
                .toList();
    }
}
