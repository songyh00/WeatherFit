package com.weatherfit.backend.recommend.service;

import com.weatherfit.backend.recommend.repository.OutfitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;

/**
 * 전체 코디 추천 관련 서비스
 */
@Service
@RequiredArgsConstructor
public class OutfitService {

    private final OutfitRepository outfitRepository;

    public List<Outfit> recommendOutfitSet(double temperature, String style, String gender) {
        List<Outfit> outfits;

        if (style == null || style.isBlank()) {
            // 스타일 선택 안 한 경우 → 전체 스타일 추천
            outfits = outfitRepository.findOutfitByTemperatureAndGender(temperature, gender);
        } else {
            // 스타일 선택한 경우 → 해당 스타일만 추천
            outfits = outfitRepository.findOutfitByTemperatureAndStyleAndGender(temperature, style, gender);
        }

        if (outfits.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "추천 결과가 없습니다.");
        }

        Collections.shuffle(outfits); // 리스트 무작위 섞기
        return outfits.stream()
                .limit(3) // 3개만 추출
                .toList();
    }
}
