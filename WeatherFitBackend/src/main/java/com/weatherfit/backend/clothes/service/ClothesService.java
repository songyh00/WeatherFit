package com.weatherfit.backend.clothes.service;

import com.weatherfit.backend.clothes.dto.ClothesRecommendRequestDto;
import com.weatherfit.backend.clothes.dto.ClothesRecommendResponseDto;
import com.weatherfit.backend.clothes.entity.Clothes;
import com.weatherfit.backend.clothes.repository.ClothesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ClothesService {

    @Autowired
    private ClothesRepository clothesRepository;

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
            case "BEST":
                return recommendBest(averageTemperature, gender);
            case "RECOMMEND":
                return recommendRandom(averageTemperature, gender);
            case "OUTER":
                return recommendOuter(averageTemperature, gender);
            case "TOP":
                return recommendTop(averageTemperature, gender);
            case "BOTTOM":
                return recommendBottom(averageTemperature, gender);
            default:
                throw new IllegalArgumentException("잘못된 배너 타입입니다: " + bannerType);
        }
    }

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
                .filter(clothes -> "원피스".equals(clothes.getCategory()))
                .count();

        int bottomCount = 3 - (int) dressCount;
        if (bottomCount > 0) {
            List<Clothes> bottomList = clothesRepository.findByCategoryAndGenderInOrderByLikeCountDesc("하의", genderList);
            bottomList = filterByTemperature(bottomList, averageTemperature);
            recommended.addAll(pickTopClothes(bottomList, bottomCount));
        }

        return buildResponse(recommended);
    }

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
                .filter(clothes -> "원피스".equals(clothes.getCategory()))
                .count();

        int bottomCount = 3 - (int) dressCount;
        if (bottomCount > 0) {
            List<Clothes> bottomList = clothesRepository.findByCategoryAndGenderIn("하의", genderList);
            bottomList = filterByTemperature(bottomList, averageTemperature);
            recommended.addAll(pickRandomClothes(bottomList, bottomCount));
        }

        return buildResponse(recommended);
    }

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

    private ClothesRecommendResponseDto recommendBottom(double averageTemperature, String gender) {
        List<String> genderList = getGenderList(gender);
        List<Clothes> bottomList = clothesRepository.findByCategoryAndGenderIn("하의", genderList);
        bottomList = filterByTemperature(bottomList, averageTemperature);
        List<Clothes> recommended = pickRandomClothes(bottomList, 3);

        return buildResponse(recommended);
    }

    private List<Clothes> filterByTemperature(List<Clothes> clothesList, double averageTemperature) {
        return clothesList.stream()
                .filter(c -> c.getMinTemperature() <= averageTemperature && averageTemperature <= c.getMaxTemperature())
                .collect(Collectors.toList());
    }

    private List<Clothes> pickTopClothes(List<Clothes> clothesList, int limit) {
        Map<Integer, List<Clothes>> grouped = clothesList.stream()
                .collect(Collectors.groupingBy(Clothes::getLikeCount,
                        () -> new TreeMap<>(Collections.reverseOrder()),
                        Collectors.toList()));

        List<Clothes> result = new ArrayList<>();

        for (List<Clothes> group : grouped.values()) {
            Collections.shuffle(group);
            for (Clothes clothes : group) {
                if (result.size() >= limit) {
                    return result;
                }
                result.add(clothes);
            }
        }

        return result;
    }

    private List<Clothes> pickRandomClothes(List<Clothes> clothesList, int limit) {
        List<Clothes> copy = new ArrayList<>(clothesList);
        Collections.shuffle(copy);
        return copy.stream().limit(limit).toList();
    }

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

    private List<String> getGenderList(String gender) {
        if ("FEMALE".equalsIgnoreCase(gender)) {
            return Arrays.asList("FEMALE", "UNISEX");
        } else {
            return Arrays.asList("MALE", "UNISEX");
        }
    }
}
