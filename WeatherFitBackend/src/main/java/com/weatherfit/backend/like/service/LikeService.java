package com.weatherfit.backend.like.service;

import com.weatherfit.backend.clothes.entity.Clothes;
import com.weatherfit.backend.clothes.repository.ClothesRepository;
import com.weatherfit.backend.like.dto.LikeDto;
import com.weatherfit.backend.like.entity.Like;
import com.weatherfit.backend.like.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final ClothesRepository clothesRepository;

    public void toggleLike(Long userId, Long clothesId) {
        Clothes clothes = clothesRepository.findById(clothesId)
                .orElseThrow(() -> new IllegalArgumentException("옷을 찾을 수 없습니다."));

        Like like = likeRepository.findByUserIdAndClothesId(userId, clothesId)
                .orElse(null);

        if (like != null) {
            clothes.decreaseLikeCount();
            likeRepository.delete(like);
        } else {
            clothes.increaseLikeCount();
            Like newLike = new Like(userId, clothes);
            likeRepository.save(newLike);
        }
    }

    public List<LikeDto> getMyLikes(Long userId) {
        List<Like> likes = likeRepository.findByUserId(userId);

        return likes.stream()
                .map(like -> {
                    Clothes clothes = like.getClothes();
                    return new LikeDto(
                            clothes.getId(),
                            clothes.getImageUrl(),
                            clothes.getName(),
                            clothes.getCategory()
                    );
                })
                .toList();
    }

}
