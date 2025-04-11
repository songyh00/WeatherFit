package com.weatherfit.backend.auth.controller;

import com.weatherfit.backend.auth.JwtUtil;
import com.weatherfit.backend.auth.dto.SignupRequestDto;
import com.weatherfit.backend.clothes.entity.Clothes;
import com.weatherfit.backend.clothes.repository.ClothesRepository;
import com.weatherfit.backend.like.entity.Like;
import com.weatherfit.backend.like.repository.LikeRepository;
import com.weatherfit.backend.user.entity.User;
import com.weatherfit.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final LikeRepository likeRepository;
    private final ClothesRepository clothesRepository;
    private final JwtUtil jwtUtil;

    /**
     * 로그인 (username + password 입력 → 성공 시 token + username 반환)
     */
    @PostMapping("/login")
    public Map<String, String> login(@RequestParam("username") String username,
                                     @RequestParam("password") String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 사용자입니다."));

        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("비밀번호가 틀렸습니다.");
        }

        String token = jwtUtil.generateToken(user.getId(), user.getGender().toString());

        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("username", user.getUsername());
        return response;
    }

    /**
     * 회원가입 (username, email 중복 검사 후 등록)
     */
    @PostMapping("/signup")
    public String signup(@RequestBody SignupRequestDto requestDto) {
        if (userRepository.findByUsername(requestDto.getUsername()).isPresent()) {
            throw new RuntimeException("이미 존재하는 아이디입니다.");
        }
        if (userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new RuntimeException("이미 존재하는 이메일입니다.");
        }

        User newUser = new User();
        newUser.setUsername(requestDto.getUsername());
        newUser.setPassword(requestDto.getPassword()); // 비밀번호는 추후 암호화 권장
        newUser.setEmail(requestDto.getEmail());
        newUser.setGender(User.Gender.valueOf(requestDto.getGender().toUpperCase()));

        userRepository.save(newUser);

        return "회원가입이 완료되었습니다.";
    }

    /**
     * 아이디 찾기 (email + password 입력 → username 반환)
     */
    @PostMapping("/find-username")
    public String findUsername(@RequestParam("email") String email,
                               @RequestParam("password") String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("일치하는 사용자가 없습니다."));

        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("일치하는 사용자가 없습니다.");
        }

        return user.getUsername();
    }

    /**
     * 비밀번호 찾기 (username + email 입력 → password 반환)
     */
    @PostMapping("/find-password")
    public String findPassword(@RequestParam("username") String username,
                               @RequestParam("email") String email) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("일치하는 사용자가 없습니다."));

        if (!user.getEmail().equals(email)) {
            throw new RuntimeException("일치하는 사용자가 없습니다.");
        }

        return user.getPassword();
    }

    /**
     * 회원 탈퇴 (토큰 인증 → 좋아요한 옷 likeCount 감소 → 좋아요 기록 삭제 → 회원 삭제)
     */
    @DeleteMapping("/withdraw")
    @Transactional
    public String withdraw(@RequestHeader("Authorization") String token) {
        String tokenValue = token.replace("Bearer ", "");
        Long userId = jwtUtil.extractUserId(tokenValue);

        // 1. 이 유저가 누른 좋아요 조회
        List<Like> userLikes = likeRepository.findByUserId(userId);

        // 2. 좋아요한 옷들의 likeCount 감소
        for (Like like : userLikes) {
            Clothes clothes = like.getClothes();
            clothes.setLikeCount(Math.max(0, clothes.getLikeCount() - 1)); // 0보다 작아지지 않게
            clothesRepository.save(clothes);
        }

        // 3. 좋아요 기록 삭제
        likeRepository.deleteAll(userLikes);

        // 4. 회원 삭제
        userRepository.deleteById(userId);

        return "회원 탈퇴가 완료되었습니다.";
    }
}
