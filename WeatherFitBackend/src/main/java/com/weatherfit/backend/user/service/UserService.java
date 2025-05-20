package com.weatherfit.backend.user.service;

import com.weatherfit.backend.auth.JwtUtil;
import com.weatherfit.backend.common.exception.CustomException;
import com.weatherfit.backend.common.exception.ErrorCode;
import com.weatherfit.backend.user.dto.UpdateProfileRequestDto;
import com.weatherfit.backend.user.dto.UserProfileResponseDto;
import com.weatherfit.backend.user.entity.User;
import com.weatherfit.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    /**
     * 내 정보 조회
     */
    public UserProfileResponseDto getMyProfile(String token) {
        log.info("🟡 내 정보 조회 요청");
        Long userId = jwtUtil.extractUserId(token);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.warn("🟠 내 정보 조회 실패 (아이디 없음): userId={}", userId);
                    throw new CustomException(ErrorCode.USER_NOT_FOUND);
                });

        log.info("🟢 내 정보 조회 성공: userId={}", userId);
        return new UserProfileResponseDto(
                user.getUsername(),
                user.getEmail(),
                user.getGender()
        );
    }

    /**
     * 내 정보 수정 (이메일, 성별)
     */
    @Transactional
    public void updateProfile(String token, UpdateProfileRequestDto requestDto) {
        log.info("🟡 내 정보 수정 요청");
        Long userId = jwtUtil.extractUserId(token);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.warn("🟠 사용자 정보 수정 실패 (아이디 없음): userId={}", userId);
                    throw new CustomException(ErrorCode.USER_NOT_FOUND);
                });

        String newEmail = requestDto.getEmail();
        if (newEmail != null && !newEmail.isBlank()) {
            // ✅ 본인 이메일이 아니라면 중복 체크
            userRepository.findByEmail(newEmail).ifPresent(existingUser -> {
                if (!existingUser.getId().equals(userId)) {
                    log.warn("🟠 사용자 정보 수정 실패 (이메일 중복): userId={}, newEmail={}", userId, newEmail);
                    throw new CustomException(ErrorCode.EMAIL_ALREADY_EXISTS);
                }
            });

            user.setEmail(newEmail);
        }

        if (requestDto.getGender() != null) {
            user.setGender(requestDto.getGender());
        }

        log.info("🟢 사용자 정보 변경 성공: userId={} email={} gender={}",
                userId, user.getEmail(), user.getGender());
    }
}
