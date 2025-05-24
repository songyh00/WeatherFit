package com.weatherfit.backend.auth.service;

import com.weatherfit.backend.auth.JwtUtil;
import com.weatherfit.backend.auth.dto.*;
import com.weatherfit.backend.clothes.repository.ClothesRepository;
import com.weatherfit.backend.common.enumtype.Gender;
import com.weatherfit.backend.common.exception.CustomException;
import com.weatherfit.backend.common.exception.ErrorCode;
import com.weatherfit.backend.like.repository.LikeRepository;
import com.weatherfit.backend.user.entity.User;
import com.weatherfit.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final LikeRepository likeRepository;
    private final ClothesRepository clothesRepository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder;

    public void signup(SignupRequestDto requestDto) {
        log.info("🟡 회원가입 시도: username={}", requestDto.getUsername());

        if (userRepository.findByUsername(requestDto.getUsername()).isPresent()) {
            log.warn("🟠 회원가입 실패 (아이디 중복): username={}", requestDto.getUsername());
            throw new CustomException(ErrorCode.USERNAME_ALREADY_EXISTS);
        }
        if (userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            log.warn("🟠 회원가입 실패 (이메일 중복): email={}", requestDto.getEmail());
            throw new CustomException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }

        Gender gender = Gender.from(requestDto.getGender());
        if (gender == Gender.UNISEX) {
            log.warn("🟠 회원가입 실패 (잘못된 성별 선택): username={}", requestDto.getUsername());
            throw new CustomException(ErrorCode.INVALID_USER_GENDER);
        }

        User newUser = new User();
        newUser.setUsername(requestDto.getUsername());
        newUser.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        newUser.setEmail(requestDto.getEmail());
        newUser.setGender(gender);

        userRepository.save(newUser);
        log.info("🟢 회원가입 성공: username={}", requestDto.getUsername());
    }

    public boolean isUsernameTaken(String username) {
        log.info("🟡 아이디 중복확인 요청: username={}", username);
        return userRepository.findByUsername(username).isPresent();
    }

    public boolean isEmailTaken(String email) {
        log.info("🟡 이메일 중복확인 요청: email={}", email);
        return userRepository.findByEmail(email).isPresent();
    }

    public String findUsername(String email, String password) {
        log.info("🟡 아이디 찾기 요청: email={}", email);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.warn("🟠 아이디 찾기 실패 (이메일 없음): email={}", email);
                    throw new CustomException(ErrorCode.USER_NOT_FOUND);
                });

        if (!passwordEncoder.matches(password, user.getPassword())) {
            log.warn("🟠 아이디 찾기 실패 (비밀번호 불일치): email={}", email);
            throw new CustomException(ErrorCode.PASSWORD_MISMATCH);
        }

        log.info("🟢 아이디 찾기 성공: email={}, username={}", email, user.getUsername());
        return user.getUsername();
    }

    public void verifyUser(PasswordResetVerificationRequestDto requestDto) {
        log.info("🟡 비밀번호 재설정 검증 요청: username={}, email={}", requestDto.getUsername(), requestDto.getEmail());

        User user = userRepository.findByUsername(requestDto.getUsername())
                .orElseThrow(() -> {
                    log.warn("🟠 비밀번호 재설정 검증 실패 (아이디 없음): username={}", requestDto.getUsername());
                    throw new CustomException(ErrorCode.USER_NOT_FOUND);
                });

        if (!user.getEmail().equalsIgnoreCase(requestDto.getEmail())) {
            log.warn("🟠 비밀번호 재설정 검증 실패 (이메일 불일치): username={}, email={}", requestDto.getUsername(), requestDto.getEmail());
            throw new CustomException(ErrorCode.EMAIL_MISMATCHED);
        }

        log.info("🟢 비밀번호 재설정 검증 성공: username={}, email={}", requestDto.getUsername(), requestDto.getEmail());
    }

    @Transactional
    public void resetPassword(PasswordResetRequestDto requestDto) {
        log.info("🟡 비밀번호 재설정 요청(로그인 전): username={}", requestDto.getUsername());

        User user = userRepository.findByUsername(requestDto.getUsername())
                .orElseThrow(() -> {
                    log.warn("🟠 비밀번호 재설정 실패 (아이디 없음): username={}", requestDto.getUsername());
                    throw new CustomException(ErrorCode.USER_NOT_FOUND);
                });

        if (!requestDto.getNewPassword().equals(requestDto.getNewPasswordConfirm())) {
            log.warn("🟠 비밀번호 재설정 실패 (비밀번호 불일치): username={}", requestDto.getUsername());
            throw new CustomException(ErrorCode.NEW_PASSWORD_MISMATCH);
        }

        user.setPassword(passwordEncoder.encode(requestDto.getNewPassword()));
        log.info("🟢 비밀번호 재설정 성공(로그인 전): username={}", requestDto.getUsername());
    }

    public LoginResponseDto login(LoginRequestDto requestDto) {
        log.info("🟡 로그인 시도: username={}", requestDto.getUsername());

        User user = userRepository.findByUsername(requestDto.getUsername())
                .orElseThrow(() -> {
                    log.warn("🟠 로그인 실패 (아이디 없음): username={}", requestDto.getUsername());
                    throw new CustomException(ErrorCode.USER_NOT_FOUND);
                });

        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            log.warn("🟠 로그인 실패 (비밀번호 틀림): username={}", requestDto.getUsername());
            throw new CustomException(ErrorCode.PASSWORD_MISMATCH);
        }

        String token = jwtUtil.generateToken(user.getId());
        log.info("🟢 로그인 성공: username={}", user.getUsername());

        return new LoginResponseDto(token);
    }

    @Transactional
    public void changeMyPassword(String token, PasswordChangeRequestDto requestDto) {
        log.info("🟡 비밀번호 변경 요청(로그인 후)");

        Long userId = jwtUtil.extractUserId(token);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.warn("🟠 비밀번호 변경 실패 (아이디 없음): userId={}", userId);
                    throw new CustomException(ErrorCode.USER_NOT_FOUND);
                });

        if (requestDto.getOldPassword() == null) {
            log.warn("🟠 비밀번호 변경 실패 (기존 비밀번호 null): userId={}", userId);
            throw new CustomException(ErrorCode.OLD_PASSWORD_REQUIRED);
        }

        if (!passwordEncoder.matches(requestDto.getOldPassword(), user.getPassword())) {
            log.warn("🟠 비밀번호 변경 실패 (현재 비밀번호 불일치): userId={}", userId);
            throw new CustomException(ErrorCode.PASSWORD_MISMATCH);
        }

        if (!requestDto.getNewPassword().equals(requestDto.getNewPasswordConfirm())) {
            log.warn("🟠 비밀번호 변경 실패 (비밀번호 불일치): userId={}", userId);
            throw new CustomException(ErrorCode.NEW_PASSWORD_MISMATCH);
        }

        user.setPassword(passwordEncoder.encode(requestDto.getNewPassword()));
        log.info("🟢 비밀번호 변경 성공(로그인 후): userId={}", userId);
    }

    @Transactional
    public void withdraw(String token) {
        Long userId = jwtUtil.extractUserId(token);
        log.info("🟡 회원 탈퇴 요청: userId={}", userId);

        clothesRepository.decreaseLikeCountByUserId(userId);
        likeRepository.deleteByUserId(userId);
        userRepository.deleteById(userId);

        log.info("🟢 회원 탈퇴 성공: userId={}", userId);
    }
}
