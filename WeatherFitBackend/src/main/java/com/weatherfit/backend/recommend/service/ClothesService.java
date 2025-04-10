package com.weatherfit.backend.recommend.service;

import com.weatherfit.backend.recommend.dto.ClothesDTO;
import com.weatherfit.backend.recommend.domain.Clothes;
import com.weatherfit.backend.recommend.repository.ClothesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClothesService {

    private final ClothesRepository clothesRepository;

    private static final int TEMPERATURE_THRESHOLD = 21;

    private static final String OUTER = "아우터";
    private static final String TOP = "상의";
    private static final String BOTTOM = "하의";
    private static final String DRESS = "원피스";

    public List<ClothesDTO> recommendBest(String gender, int temperature) {
        List<Clothes> clothes = clothesRepository.findBestClothesByLikes(gender);
        return selectClothesByRules(clothes, gender, temperature, true);
    }

    public List<ClothesDTO> recommendRandom(String gender, int temperature) {
        List<Clothes> clothes = clothesRepository.findAllRandom(gender);
        return selectClothesByRules(clothes, gender, temperature, false);
    }

    public Object recommendOuter(int temperature) {
        if (temperature >= TEMPERATURE_THRESHOLD) {
            return "아우터를 입기에는 날씨가 덥습니다. 그래도 입으시려면 얇은 아우터를 추천드립니다 :)";
        }
        return pickRandom(clothesRepository.findByCategory(OUTER), 3);
    }

    public List<ClothesDTO> recommendTop(String gender) {
        List<Clothes> tops = clothesRepository.findByCategory(TOP);
        if (gender.equalsIgnoreCase("FEMALE")) {
            tops.addAll(clothesRepository.findByCategory(DRESS));
        }
        return pickRandom(tops, 3);
    }

    public List<ClothesDTO> recommendBottom() {
        return pickRandom(clothesRepository.findByCategory(BOTTOM), 3);
    }

    private List<ClothesDTO> selectClothesByRules(List<Clothes> clothes, String gender, int temperature, boolean best) {
        List<Clothes> outers = new ArrayList<>();
        List<Clothes> tops = new ArrayList<>();
        List<Clothes> bottoms = new ArrayList<>();
        List<Clothes> dresses = new ArrayList<>();

        for (Clothes c : clothes) {
            switch (c.getCategory()) {
                case OUTER -> outers.add(c);
                case TOP -> tops.add(c);
                case BOTTOM -> bottoms.add(c);
                case DRESS -> dresses.add(c);
            }
        }

        List<ClothesDTO> result = new ArrayList<>();

        if (temperature < TEMPERATURE_THRESHOLD) {
            result.addAll(pickTop(outers, 3, best));
        }

        if (gender.equalsIgnoreCase("FEMALE")) {
            tops.addAll(dresses);
        }

        List<ClothesDTO> selectedTops = pickTop(tops, 3, best);
        int dressCount = (int) selectedTops.stream()
                .filter(dto -> DRESS.equals(dto.getCategory()))
                .count();
        result.addAll(selectedTops);

        result.addAll(pickTop(bottoms, 3 - dressCount, best));

        return result;
    }

    private List<ClothesDTO> pickTop(List<Clothes> clothes, int count, boolean best) {
        if (clothes.isEmpty()) return List.of();
        if (best) {
            // [수정] 좋아요 수 기준 그룹핑하고, 동률끼리 랜덤 섞기
            Map<Integer, List<Clothes>> grouped = clothes.stream()
                    .collect(Collectors.groupingBy(Clothes::getLikes));

            List<Integer> sortedLikes = new ArrayList<>(grouped.keySet());
            sortedLikes.sort(Comparator.reverseOrder());  // 좋아요 높은 순

            List<Clothes> sortedAndShuffled = new ArrayList<>();
            for (Integer likes : sortedLikes) {
                List<Clothes> sameLikeClothes = grouped.get(likes);
                Collections.shuffle(sameLikeClothes);  // 동률끼리 랜덤 섞기
                sortedAndShuffled.addAll(sameLikeClothes);
            }

            return sortedAndShuffled.stream()
                    .limit(count)
                    .map(ClothesDTO::new)
                    .toList();
        } else {
            Collections.shuffle(clothes);
            return clothes.stream()
                    .limit(count)
                    .map(ClothesDTO::new)
                    .toList();
        }
    }

    private List<ClothesDTO> pickRandom(List<Clothes> clothes, int count) {
        Collections.shuffle(clothes);
        return clothes.stream()
                .limit(count)
                .map(ClothesDTO::new)
                .toList();
    }
}
