package com.weatherfit.backend.auth.service;

import com.weatherfit.backend.auth.JwtUtil;
import com.weatherfit.backend.auth.dto.ChangePasswordRequestDto;
import com.weatherfit.backend.auth.dto.LoginRequestDto;
import com.weatherfit.backend.auth.dto.LoginResponseDto;
import com.weatherfit.backend.auth.dto.SignupRequestDto;
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

import java.util.List;

/**
 * 회원 관련 비즈니스 로직을 처리하는 서비스
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final LikeRepository likeRepository;
    private final ClothesRepository clothesRepository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * 회원가입 처리
     */
    public void signup(SignupRequestDto requestDto) {
        log.info("🟢 회원가입 시도: username={}", requestDto.getUsername());

        if (userRepository.findByUsername(requestDto.getUsername()).isPresent()) {
            log.warn("🟠 회원가입 실패 (아이디 중복): username={}", requestDto.getUsername());
            throw new CustomException(ErrorCode.USERNAME_ALREADY_EXISTS);
        }
        if (userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            log.warn("🟠 회원가입 실패 (이메일 중복): email={}", requestDto.getEmail());
            throw new CustomException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }

        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

        Gender gender = Gender.from(requestDto.getGender());
        if (gender == Gender.UNISEX) {
            log.warn("🟠 회원가입 실패 (잘못된 성별 선택): username={}", requestDto.getUsername());
            throw new CustomException(ErrorCode.INVALID_USER_GENDER);
        }

        User newUser = new User();
        newUser.setUsername(requestDto.getUsername());
        newUser.setPassword(encodedPassword);
        newUser.setEmail(requestDto.getEmail());
        newUser.setGender(gender);

        userRepository.save(newUser);
    }

    /**
     * 아이디 중복확인
     */
    public boolean isUsernameTaken(String username) {
        log.info("🟡 아이디 중복확인 요청: username={}", username);
        return userRepository.findByUsername(username).isPresent();
    }

    /**
     * 이메일 중복확인
     */
    public boolean isEmailTaken(String email) {
        log.info("🟡 이메일 중복확인 요청: email={}", email);
        return userRepository.findByEmail(email).isPresent();
    }

    /**
     * 로그인 처리
     */
    public LoginResponseDto login(LoginRequestDto requestDto) {
        log.info("🔵 로그인 시도: username={}", requestDto.getUsername());

        User user = userRepository.findByUsername(requestDto.getUsername())
                .orElseThrow(() -> {
                    log.warn("🟠 로그인 실패 (아이디 없음): username={}", requestDto.getUsername());
                    throw new CustomException(ErrorCode.USER_NOT_FOUND);
                });

        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            log.warn("🟠 로그인 실패 (비밀번호 틀림): username={}", requestDto.getUsername());
            throw new CustomException(ErrorCode.PASSWORD_MISMATCH);
        }

        String token = jwtUtil.generateToken(user.getId(), user.getGender().toString());
        return new LoginResponseDto(token, user.getUsername(), user.getEmail(), user.getGender().toString());
    }

    /**
     * 아이디 찾기
     */
    public String findUsername(String email, String password) {
        log.info("🔵 아이디 찾기 요청: email={}", email);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new CustomException(ErrorCode.PASSWORD_MISMATCH);
        }

        return user.getUsername();
    }

    /**
     * 비밀번호 재설정 검증
     */
    public void verifyUser(String username, String email) {
        log.info("🟡 비밀번호 재설정 검증 요청: username={}, email={}", username, email);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (!user.getEmail().equalsIgnoreCase(email)) {
            throw new CustomException(ErrorCode.EMAIL_NOT_MATCHED);
        }
    }

    /**
     * 비밀번호 재설정
     */
    @Transactional
    public void changePassword(String username, String newPassword) {
        log.info("🟢 비밀번호 변경 요청: username={}", username);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
    }

    /**
     * 로그인 후 비밀번호 변경
     */
    @Transactional
    public void changeMyPassword(String token, ChangePasswordRequestDto requestDto) {
        Long userId = jwtUtil.extractUserId(token);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(requestDto.getOldPassword(), user.getPassword())) {
            log.warn("🟠 비밀번호 변경 실패 (현재 비밀번호 불일치): userId={}", userId);
            throw new CustomException(ErrorCode.PASSWORD_MISMATCH);
        }

        String encodedNewPassword = passwordEncoder.encode(requestDto.getNewPassword());
        user.setPassword(encodedNewPassword);
        log.info("🟢 비밀번호 변경 성공: userId={}", userId);
    }

    /**
     * 회원 탈퇴 처리
     */
    @Transactional
    public void withdraw(String token) {
        Long userId = jwtUtil.extractUserId(token);
        log.info("🔴 회원 탈퇴 요청: userId={}", userId);

        // 좋아요 누른 옷들의 좋아요 수 한 번에 감소 (최적화)
        clothesRepository.decreaseLikeCountByUserId(userId);

        // 좋아요 기록 삭제
        likeRepository.deleteByUserId(userId);

        // 회원 삭제
        userRepository.deleteById(userId);
    }

}