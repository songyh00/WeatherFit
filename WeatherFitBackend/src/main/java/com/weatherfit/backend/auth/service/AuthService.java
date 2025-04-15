package com.weatherfit.backend.auth.service;

import com.weatherfit.backend.auth.dto.LoginRequestDto;
import com.weatherfit.backend.auth.dto.SignupRequestDto;
import com.weatherfit.backend.auth.JwtUtil;
import com.weatherfit.backend.clothes.entity.Clothes;
import com.weatherfit.backend.clothes.repository.ClothesRepository;
import com.weatherfit.backend.like.entity.Like;
import com.weatherfit.backend.like.repository.LikeRepository;
import com.weatherfit.backend.user.entity.User;
import com.weatherfit.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 회원(Auth) 관련 비즈니스 로직을 처리하는 서비스
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final LikeRepository likeRepository;
    private final ClothesRepository clothesRepository;
    private final JwtUtil jwtUtil;

    /**
     * 로그인 처리
     * @param requestDto 로그인 요청 정보
     * @return JWT 토큰 반환
     */
    public String login(LoginRequestDto requestDto) {
        log.info("🔵 로그인 시도: username={}", requestDto.getUsername());

        User user = userRepository.findByUsername(requestDto.getUsername())
                .orElseThrow(() -> {
                    log.warn("🟠 로그인 실패 (아이디 없음): username={}", requestDto.getUsername());
                    return new RuntimeException("존재하지 않는 사용자입니다.");
                });

        if (!user.getPassword().equals(requestDto.getPassword())) {
            log.warn("🟠 로그인 실패 (비밀번호 틀림): username={}", requestDto.getUsername());
            throw new RuntimeException("비밀번호가 틀렸습니다.");
        }

        return jwtUtil.generateToken(user.getId(), user.getGender().toString());
    }

    /**
     * 회원가입 처리
     * @param requestDto 회원가입 요청 정보
     */
    public void signup(SignupRequestDto requestDto) {
        log.info("🟢 회원가입 시도: username={}", requestDto.getUsername());

        if (userRepository.findByUsername(requestDto.getUsername()).isPresent()) {
            log.warn("🟠 회원가입 실패 (아이디 중복): username={}", requestDto.getUsername());
            throw new RuntimeException("이미 존재하는 아이디입니다.");
        }
        if (userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            log.warn("🟠 회원가입 실패 (이메일 중복): email={}", requestDto.getEmail());
            throw new RuntimeException("이미 존재하는 이메일입니다.");
        }

        User newUser = new User();
        newUser.setUsername(requestDto.getUsername());
        newUser.setPassword(requestDto.getPassword());
        newUser.setEmail(requestDto.getEmail());
        newUser.setGender(User.Gender.valueOf(requestDto.getGender().toUpperCase())); // MALE, FEMALE 변환

        userRepository.save(newUser);
    }

    /**
     * 이메일 + 비밀번호로 아이디 찾기
     * @param email 사용자 이메일
     * @param password 사용자 비밀번호
     * @return username 반환
     */
    public String findUsername(String email, String password) {
        log.info("🔵 아이디 찾기 요청: email={}", email);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("일치하는 사용자가 없습니다."));

        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("일치하는 사용자가 없습니다.");
        }

        return user.getUsername();
    }

    /**
     * 아이디 중복확인
     * @param username 확인할 사용자명
     * @return true(중복됨) / false(사용 가능)
     */
    public boolean isUsernameTaken(String username) {
        log.info("🟡 아이디 중복확인 요청: username={}", username);
        return userRepository.findByUsername(username).isPresent();
    }

    /**
     * 이메일 중복확인
     * @param email 확인할 이메일
     * @return true(중복됨) / false(사용 가능)
     */
    public boolean isEmailTaken(String email) {
        log.info("🟡 이메일 중복확인 요청: email={}", email);
        return userRepository.findByEmail(email).isPresent();
    }

    /**
     * 아이디 + 이메일로 비밀번호 찾기
     * @param username 사용자 아이디
     * @param email 사용자 이메일
     * @return password 반환
     */
    public String findPassword(String username, String email) {
        log.info("🔵 비밀번호 찾기 요청: username={}", username);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("일치하는 사용자가 없습니다."));

        if (!user.getEmail().equals(email)) {
            throw new RuntimeException("일치하는 사용자가 없습니다.");
        }

        return user.getPassword();
    }

    /**
     * 회원 탈퇴 처리
     * - 사용자가 누른 좋아요 기록 삭제
     * - 옷 좋아요 수 감소
     * - 회원 삭제
     * @param token Authorization 헤더에 담긴 JWT 토큰
     */
    @Transactional
    public void withdraw(String token) {
        Long userId = jwtUtil.extractUserId(token);
        log.info("🔴 회원 탈퇴 요청: userId={}", userId);

        List<Like> userLikes = likeRepository.findByUserId(userId);

        for (Like like : userLikes) {
            Clothes clothes = like.getClothes();
            clothes.setLikeCount(Math.max(0, clothes.getLikeCount() - 1)); // 좋아요 수 감소
            clothesRepository.save(clothes);
        }

        likeRepository.deleteAll(userLikes); // 좋아요 기록 삭제
        userRepository.deleteById(userId);   // 사용자 계정 삭제
    }

}
