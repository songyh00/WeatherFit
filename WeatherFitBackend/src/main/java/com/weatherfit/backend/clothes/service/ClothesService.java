package com.weatherfit.backend.clothes.service;

import com.weatherfit.backend.clothes.dto.ClothesRecommendRequestDto;
import com.weatherfit.backend.clothes.dto.ClothesRecommendResponseDto;
import com.weatherfit.backend.clothes.entity.Clothes;
import com.weatherfit.backend.clothes.repository.ClothesRepository;
import com.weatherfit.backend.common.enumtype.Gender;
import com.weatherfit.backend.common.exception.CustomException;
import com.weatherfit.backend.common.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 코디 추천을 담당하는 서비스
 */
@Slf4j
@Service
public class ClothesService {

    @Autowired
    private ClothesRepository clothesRepository;

    // 평균 기온 기준 (아우터 추천 제외용)
    private static final double HOT_TEMPERATURE_THRESHOLD = 21.0;

    /**
     * 배너 타입과 내일 여부에 따라 코디 추천
     */
    public ClothesRecommendResponseDto recommendClothes(ClothesRecommendRequestDto requestDto,
                                                        double averageTemperature,
                                                        String gender,
                                                        String bannerType,
                                                        boolean tomorrow) {
        if (bannerType == null) {
            throw new CustomException(ErrorCode.INVALID_BANNER_TYPE);
        }

        Gender genderEnum = Gender.from(gender);

        return tomorrow
                ? recommendTomorrowClothes(averageTemperature, genderEnum, bannerType)
                : recommendTodayClothes(averageTemperature, genderEnum, bannerType);
    }

    /**
     * 내일 코디 추천
     */
    private ClothesRecommendResponseDto recommendTomorrowClothes(double averageTemperature, Gender genderEnum, String bannerType) {
        return switch (bannerType.toUpperCase()) {
            case "BEST" -> recommendBest(averageTemperature, genderEnum);
            case "RECOMMEND" -> recommendRandom(averageTemperature, genderEnum);
            case "OUTER" -> recommendOuter(averageTemperature, genderEnum);
            case "TOP" -> recommendTop(averageTemperature, genderEnum);
            case "BOTTOM" -> recommendBottom(averageTemperature, genderEnum);
            default -> throw new CustomException(ErrorCode.INVALID_BANNER_TYPE);
        };
    }

    /**
     * 오늘 코디 추천
     */
    private ClothesRecommendResponseDto recommendTodayClothes(double averageTemperature, Gender genderEnum, String bannerType) {
        return switch (bannerType.toUpperCase()) {
            case "BEST" -> recommendBest(averageTemperature, genderEnum);
            case "RECOMMEND" -> recommendRandom(averageTemperature, genderEnum);
            case "OUTER" -> recommendOuter(averageTemperature, genderEnum);
            case "TOP" -> recommendTop(averageTemperature, genderEnum);
            case "BOTTOM" -> recommendBottom(averageTemperature, genderEnum);
            default -> throw new CustomException(ErrorCode.INVALID_BANNER_TYPE);
        };
    }

    /**
     * BEST 배너 추천 (좋아요 순)
     */
    private ClothesRecommendResponseDto recommendBest(double averageTemperature, Gender genderEnum) {
        List<Clothes> recommended = new ArrayList<>();
        boolean isHot = averageTemperature >= HOT_TEMPERATURE_THRESHOLD;
        List<String> genderList = getGenderList(genderEnum);

        List<String> topCategories = new ArrayList<>(Collections.singletonList("상의"));
        if (genderEnum == Gender.FEMALE) {
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
     * 추천 배너 (랜덤 추천)
     */
    private ClothesRecommendResponseDto recommendRandom(double averageTemperature, Gender genderEnum) {
        List<Clothes> recommended = new ArrayList<>();
        boolean isHot = averageTemperature >= HOT_TEMPERATURE_THRESHOLD;
        List<String> genderList = getGenderList(genderEnum);

        List<String> topCategories = new ArrayList<>(Collections.singletonList("상의"));
        if (genderEnum == Gender.FEMALE) {
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
    private ClothesRecommendResponseDto recommendOuter(double averageTemperature, Gender genderEnum) {
        boolean isHot = averageTemperature >= HOT_TEMPERATURE_THRESHOLD;
        List<String> genderList = getGenderList(genderEnum);

        List<Clothes> recommended = new ArrayList<>();
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
    private ClothesRecommendResponseDto recommendTop(double averageTemperature, Gender genderEnum) {
        List<String> categories = new ArrayList<>(Collections.singletonList("상의"));
        if (genderEnum == Gender.FEMALE) {
            categories.add("원피스");
        }
        List<String> genderList = getGenderList(genderEnum);

        List<Clothes> topList = clothesRepository.findByCategoryInAndGenderIn(categories, genderList);
        topList = filterByTemperature(topList, averageTemperature);
        List<Clothes> recommended = pickRandomClothes(topList, 3);

        return buildResponse(recommended);
    }

    /**
     * 하의 배너 추천
     */
    private ClothesRecommendResponseDto recommendBottom(double averageTemperature, Gender genderEnum) {
        List<String> genderList = getGenderList(genderEnum);
        List<Clothes> bottomList = clothesRepository.findByCategoryAndGenderIn("하의", genderList);
        bottomList = filterByTemperature(bottomList, averageTemperature);
        List<Clothes> recommended = pickRandomClothes(bottomList, 3);

        return buildResponse(recommended);
    }

    /**
     * 평균 기온에 맞는 옷 필터링
     */
    private List<Clothes> filterByTemperature(List<Clothes> clothesList, double averageTemperature) {
        return clothesList.stream()
                .filter(c -> c.getMinTemperature() <= averageTemperature && averageTemperature <= c.getMaxTemperature())
                .collect(Collectors.toList());
    }

    /**
     * 좋아요 수 기준 추천 (동일 좋아요 수끼리는 랜덤)
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

    /**
     * 랜덤 추천
     */
    private List<Clothes> pickRandomClothes(List<Clothes> clothesList, int limit) {
        List<Clothes> copy = new ArrayList<>(clothesList);
        Collections.shuffle(copy);
        return copy.stream().limit(limit).toList();
    }

    /**
     * 추천 결과 DTO로 변환
     */
    private ClothesRecommendResponseDto buildResponse(List<Clothes> clothesList) {
        List<ClothesRecommendResponseDto.RecommendedClothesDto> recommended = new ArrayList<>();
        for (Clothes clothes : clothesList) {
            recommended.add(
                    ClothesRecommendResponseDto.RecommendedClothesDto.builder()
                            .id(clothes.getId())
                            .name(clothes.getName())
                            .category(clothes.getCategory())
                            .imageUrl(clothes.getImage())
                            .likeCount(clothes.getLikeCount())
                            .build()
            );
        }
        return ClothesRecommendResponseDto.builder()
                .recommendedClothes(recommended)
                .build();
    }

    /**
     * 성별에 따라 조회할 성별 리스트 반환
     */
    private List<String> getGenderList(Gender genderEnum) {
        if (genderEnum == Gender.FEMALE) {
            return Arrays.asList("FEMALE", "UNISEX");
        } else {
            return Arrays.asList("MALE", "UNISEX");
        }
    }
}