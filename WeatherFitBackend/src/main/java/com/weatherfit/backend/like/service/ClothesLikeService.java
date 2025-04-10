package com.weatherfit.backend.like.service;

import com.weatherfit.backend.like.domain.ClothesLike;
import com.weatherfit.backend.like.repository.ClothesLikeRepository;
import com.weatherfit.backend.recommend.domain.Clothes;
import com.weatherfit.backend.recommend.repository.ClothesRepository;
import com.weatherfit.backend.recommend.dto.ClothesDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClothesLikeService {

    private final ClothesLikeRepository clothesLikeRepository;
    private final ClothesRepository clothesRepository;

    @Transactional
    public boolean toggleLike(Long usersId, Long clothesId) {
        Clothes clothes = clothesRepository.findById(clothesId)
                .orElseThrow(() -> new IllegalArgumentException("해당 옷이 존재하지 않습니다."));

        return clothesLikeRepository.findByUsersIdAndClothesId(usersId, clothesId)
                .map(existingLike -> { // 이미 좋아요를 누른 경우
                    clothesLikeRepository.delete(existingLike);
                    clothes.setLikes(clothes.getLikes() - 1);
                    return false; // 좋아요 취소됨
                })
                .orElseGet(() -> { // 좋아요를 누르지 않은 경우
                    clothesLikeRepository.save(new ClothesLike(usersId, clothesId));
                    clothes.setLikes(clothes.getLikes() + 1);
                    return true; // 좋아요 추가됨
                });
    }

    public List<ClothesDTO> getUserLikedClothes(Long usersId) {
        List<ClothesLike> likes = clothesLikeRepository.findByUsersId(usersId);

        List<Long> clothesIds = likes.stream()
                .map(ClothesLike::getClothesId)
                .toList();

        List<Clothes> clothesList = clothesRepository.findAllById(clothesIds);

        return clothesList.stream()
                .map(ClothesDTO::new)
                .toList();
    }
}
