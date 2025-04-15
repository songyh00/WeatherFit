package com.weatherfit.backend.like.service;

import com.weatherfit.backend.clothes.entity.Clothes;
import com.weatherfit.backend.clothes.repository.ClothesRepository;
import com.weatherfit.backend.like.dto.LikeDto;
import com.weatherfit.backend.like.entity.Like;
import com.weatherfit.backend.like.repository.LikeRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 좋아요(Like) 관련 비즈니스 로직을 담당하는 서비스
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final ClothesRepository clothesRepository;

    /**
     * 좋아요 토글 기능
     * - 이미 좋아요를 눌렀으면 취소
     * - 좋아요가 없으면 추가
     *
     * @param userId    사용자 ID
     * @param clothesId 옷 ID
     */
    public void toggleLike(Long userId, Long clothesId) {
        Clothes clothes = clothesRepository.findById(clothesId)
                .orElseThrow(() -> new IllegalArgumentException("옷을 찾을 수 없습니다."));

        Like like = likeRepository.findByUserIdAndClothesId(userId, clothesId)
                .orElse(null);

        if (like != null) {
            clothes.decreaseLikeCount();
            likeRepository.delete(like);
            log.info("🟡 좋아요 취소: userId={}, clothesId={}", userId, clothesId);
        } else {
            clothes.increaseLikeCount();
            Like newLike = new Like(userId, clothes);
            likeRepository.save(newLike);
            log.info("🟢 좋아요 추가: userId={}, clothesId={}", userId, clothesId);
        }
    }

    /**
     * 사용자가 누른 모든 좋아요 코디 조회
     * - 옷 정보(아이디, 이미지, 이름, 카테고리)를 LikeDto로 변환하여 반환
     *
     * @param userId 사용자 ID
     * @return 좋아요한 옷 리스트 (LikeDto 목록)
     */
    public List<LikeDto> getMyLikes(Long userId) {
        List<Like> likes = likeRepository.findByUserId(userId);
        log.info("🔵 좋아요 목록 조회: userId={}, 좋아요 개수={}", userId, likes.size());

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
                .collect(Collectors.toList());
    }
}
