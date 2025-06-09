package com.weatherfit.backend.like.service;

import com.weatherfit.backend.clothes.entity.Clothes;
import com.weatherfit.backend.clothes.repository.ClothesRepository;
import com.weatherfit.backend.common.exception.CustomException;
import com.weatherfit.backend.common.exception.ErrorCode;
import com.weatherfit.backend.like.dto.LikeDto;
import com.weatherfit.backend.like.entity.Like;
import com.weatherfit.backend.like.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 좋아요(Like) 비즈니스 로직 서비스
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final ClothesRepository clothesRepository;

    @Transactional
    public void toggleLike(Long userId, Long clothesId) {
        log.info("🟡 좋아요 토글 요청: userId={}, clothesId={}", userId, clothesId);

        Clothes clothes = clothesRepository.findById(clothesId)
                .orElseThrow(() -> {
                    log.error("🔴 존재하지 않는 의류: clothesId={}", clothesId);
                    return new CustomException(ErrorCode.CLOTHES_NOT_FOUND);
                });

        Like like = likeRepository.findByUserIdAndClothesId(userId, clothesId).orElse(null);

        if (like != null) {
            clothes.decreaseLikeCount();
            likeRepository.delete(like);
            log.info("🟢 좋아요 취소 완료: userId={}, clothesId={}", userId, clothesId);
        } else {
            clothes.increaseLikeCount();
            Like newLike = new Like(userId, clothes);
            likeRepository.save(newLike);
            log.info("🟢 좋아요 추가 완료: userId={}, clothesId={}", userId, clothesId);
        }
    }

    public List<LikeDto> getMyLikes(Long userId) {
        log.info("🟡 좋아요 리스트 조회 요청: userId={}", userId);

        List<Like> likes = likeRepository.findByUserId(userId);
        log.info("🟢 좋아요 목록 조회 완료: userId={}, 총 {}개", userId, likes.size());

        return likes.stream()
                .map(like -> {
                    Clothes clothes = like.getClothes();
                    return new LikeDto(
                            clothes.getId(),
                            clothes.getImage(),
                            clothes.getName(),
                            clothes.getCategory()
                    );
                })
                .collect(Collectors.toList());
    }
}
