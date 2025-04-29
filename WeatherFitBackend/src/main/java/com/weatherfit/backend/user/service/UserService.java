package com.weatherfit.backend.user.service;

import com.weatherfit.backend.common.exception.CustomException;
import com.weatherfit.backend.common.exception.ErrorCode;
import com.weatherfit.backend.user.entity.User;
import com.weatherfit.backend.user.repository.UserRepository;
import com.weatherfit.backend.common.enumtype.Gender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 사용자(User) 관련 서비스
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /**
     * 사용자 프로필 수정 (이메일, 성별)
     */
    @Transactional
    public void updateProfile(Long userId, String newEmail, String newGender) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (newEmail != null && !newEmail.isBlank()) {
            userRepository.findByEmail(newEmail)
                    .ifPresent(existingUser -> {
                        throw new CustomException(ErrorCode.EMAIL_ALREADY_EXISTS);
                    });
            user.setEmail(newEmail);
        }

        if (newGender != null && !newGender.isBlank()) {
            try {
                user.setGender(Gender.valueOf(newGender));
            } catch (IllegalArgumentException e) {
                throw new CustomException(ErrorCode.INVALID_USER_GENDER);
            }
        }

        log.info("🔵 사용자 프로필 수정 완료: userId={}, newEmail={}, newGender={}", userId, newEmail, newGender);
    }

}