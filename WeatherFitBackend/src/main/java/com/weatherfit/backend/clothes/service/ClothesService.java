package com.weatherfit.backend.clothes.service;

import com.weatherfit.backend.clothes.dto.ClothesRecommendRequestDto;
import com.weatherfit.backend.clothes.dto.ClothesRecommendResponseDto;
import com.weatherfit.backend.clothes.entity.Clothes;
import com.weatherfit.backend.clothes.repository.ClothesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 코디 추천을 담당하는 서비스
 */
@Service
public class ClothesService {

    @Autowired
    private ClothesRepository clothesRepository;

    /**
     * 배너 타입에 따라 코디 추천
     */
    public ClothesRecommendResponseDto recommendClothes(ClothesRecommendRequestDto requestDto,
                                                        double averageTemperature,
                                                        double maxTemperature,
                                                        String weatherType,
                                                        String gender,
                                                        String bannerType) {
        if (bannerType == null) {
            throw new IllegalArgumentException("배너 타입이 필요합니다.");
        }

        switch (bannerType.toUpperCase()) {
            case "BEST": return recommendBest(averageTemperature, gender);
            case "RECOMMEND": return recommendRandom(averageTemperature, gender);
            case "OUTER": return recommendOuter(averageTemperature, gender);
            case "TOP": return recommendTop(averageTemperature, gender);
            case "BOTTOM": return recommendBottom(averageTemperature, gender);
            default: throw new IllegalArgumentException("잘못된 배너 타입입니다: " + bannerType);
        }
    }

    /**
     * BEST 배너 추천 - 좋아요 순 정렬
     */
    private ClothesRecommendResponseDto recommendBest(double averageTemperature, String gender) {
        List<Clothes> recommended = new ArrayList<>();
        boolean isHot = averageTemperature >= 21.0;
        List<String> genderList = getGenderList(gender);

        List<String> topCategories = new ArrayList<>(Arrays.asList("상의"));
        if ("FEMALE".equalsIgnoreCase(gender)) {
            topCategories.add("원피스");
        }

        if (!isHot) {
            List<Clothes> outerList = clothesRepository.findByCategoryAndGenderInOrderByLikeCountDesc("아우터", genderList);
            outerList = filterByTemperature(outerList, averageTemperature);
            recommended.addAll(pickTopClothes(outerList, 3));
        }

        List<Clothes> topList = clothesRepository.findByCategoryInAndGenderInOrderByLikeCountDesc(topCategories, genderList);
        topList = filterByTemperature(topList, averageTemperature);
        List<Clothes> pickedTop = pickTopClothes(topList, 3);
        recommended.addAll(pickedTop);

        long dressCount = pickedTop.stream()
                .filter(c -> "원피스".equals(c.getCategory()))
                .count();

        int bottomCount = 3 - (int) dressCount;
        if (bottomCount > 0) {
            List<Clothes> bottomList = clothesRepository.findByCategoryAndGenderInOrderByLikeCountDesc("하의", genderList);
            bottomList = filterByTemperature(bottomList, averageTemperature);
            recommended.addAll(pickTopClothes(bottomList, bottomCount));
        }

        return buildResponse(recommended);
    }

    /**
     * 추천 배너 - 랜덤 추천
     */
    private ClothesRecommendResponseDto recommendRandom(double averageTemperature, String gender) {
        List<Clothes> recommended = new ArrayList<>();
        boolean isHot = averageTemperature >= 21.0;
        List<String> genderList = getGenderList(gender);

        List<String> topCategories = new ArrayList<>(Arrays.asList("상의"));
        if ("FEMALE".equalsIgnoreCase(gender)) {
            topCategories.add("원피스");
        }

        if (!isHot) {
            List<Clothes> outerList = clothesRepository.findByCategoryAndGenderIn("아우터", genderList);
            outerList = filterByTemperature(outerList, averageTemperature);
            recommended.addAll(pickRandomClothes(outerList, 3));
        }

        List<Clothes> topList = clothesRepository.findByCategoryInAndGenderIn(topCategories, genderList);
        topList = filterByTemperature(topList, averageTemperature);
        List<Clothes> pickedTop = pickRandomClothes(topList, 3);
        recommended.addAll(pickedTop);

        long dressCount = pickedTop.stream()
                .filter(c -> "원피스".equals(c.getCategory()))
                .count();

        int bottomCount = 3 - (int) dressCount;
        if (bottomCount > 0) {
            List<Clothes> bottomList = clothesRepository.findByCategoryAndGenderIn("하의", genderList);
            bottomList = filterByTemperature(bottomList, averageTemperature);
            recommended.addAll(pickRandomClothes(bottomList, bottomCount));
        }

        return buildResponse(recommended);
    }

    /**
     * 아우터 배너 추천
     */
    private ClothesRecommendResponseDto recommendOuter(double averageTemperature, String gender) {
        List<Clothes> recommended = new ArrayList<>();
        boolean isHot = averageTemperature >= 21.0;
        List<String> genderList = getGenderList(gender);

        if (!isHot) {
            List<Clothes> outerList = clothesRepository.findByCategoryAndGenderIn("아우터", genderList);
            outerList = filterByTemperature(outerList, averageTemperature);
            recommended = pickRandomClothes(outerList, 3);
        }

        return buildResponse(recommended);
    }

    /**
     * 상의 배너 추천
     */
    private ClothesRecommendResponseDto recommendTop(double averageTemperature, String gender) {
        List<String> categories = new ArrayList<>(Arrays.asList("상의"));
        if ("FEMALE".equalsIgnoreCase(gender)) {
            categories.add("원피스");
        }
        List<String> genderList = getGenderList(gender);

        List<Clothes> topList = clothesRepository.findByCategoryInAndGenderIn(categories, genderList);
        topList = filterByTemperature(topList, averageTemperature);
        List<Clothes> recommended = pickRandomClothes(topList, 3);

        return buildResponse(recommended);
    }

    /**
     * 하의 배너 추천
     */
    private ClothesRecommendResponseDto recommendBottom(double averageTemperature, String gender) {
        List<String> genderList = getGenderList(gender);
        List<Clothes> bottomList = clothesRepository.findByCategoryAndGenderIn("하의", genderList);
        bottomList = filterByTemperature(bottomList, averageTemperature);
        List<Clothes> recommended = pickRandomClothes(bottomList, 3);

        return buildResponse(recommended);
    }

    /**
     * 평균 기온에 따라 필터링
     */
    private List<Clothes> filterByTemperature(List<Clothes> clothesList, double averageTemperature) {
        return clothesList.stream()
                .filter(c -> c.getMinTemperature() <= averageTemperature && averageTemperature <= c.getMaxTemperature())
                .collect(Collectors.toList());
    }

    /**
     * 좋아요 순으로 추천 (같은 좋아요 수끼리는 랜덤)
     */
    private List<Clothes> pickTopClothes(List<Clothes> clothesList, int limit) {
        Map<Integer, List<Clothes>> grouped = clothesList.stream()
                .collect(Collectors.groupingBy(
                        Clothes::getLikeCount,
                        () -> new TreeMap<>(Collections.reverseOrder()),
                        Collectors.toList()
                ));

        List<Clothes> result = new ArrayList<>();
        for (List<Clothes> group : grouped.values()) {
            Collections.shuffle(group); // 같은 좋아요 수 안에서는 랜덤
            for (Clothes clothes : group) {
                if (result.size() >= limit) {
                    return result;
                }
                result.add(clothes);
            }
        }
        return result;
    }

    /**
     * 랜덤으로 추천
     */
    private List<Clothes> pickRandomClothes(List<Clothes> clothesList, int limit) {
        List<Clothes> copy = new ArrayList<>(clothesList);
        Collections.shuffle(copy);
        return copy.stream().limit(limit).toList();
    }

    /**
     * 응답 객체 생성
     */
    private ClothesRecommendResponseDto buildResponse(List<Clothes> clothesList) {
        List<ClothesRecommendResponseDto.RecommendedClothesDto> recommended = new ArrayList<>();
        for (Clothes clothes : clothesList) {
            recommended.add(
                    ClothesRecommendResponseDto.RecommendedClothesDto.builder()
                            .id(clothes.getId())
                            .imageUrl(clothes.getImageUrl())
                            .likeCount(clothes.getLikeCount())
                            .build()
            );
        }
        return ClothesRecommendResponseDto.builder()
                .recommendedClothes(recommended)
                .build();
    }

    /**
     * 성별에 따라 필터링용 리스트 반환
     */
    private List<String> getGenderList(String gender) {
        if ("FEMALE".equalsIgnoreCase(gender)) {
            return Arrays.asList("FEMALE", "UNISEX");
        } else {
            return Arrays.asList("MALE", "UNISEX");
        }
    }
}
