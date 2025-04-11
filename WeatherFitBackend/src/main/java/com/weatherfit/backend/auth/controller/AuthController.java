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

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final LikeRepository likeRepository;
    private final ClothesRepository clothesRepository;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 사용자입니다."));

        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("비밀번호가 틀렸습니다.");
        }

        return jwtUtil.generateToken(user.getId(), user.getGender().toString());
    }

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
        newUser.setPassword(requestDto.getPassword()); // 나중에 암호화 권장
        newUser.setEmail(requestDto.getEmail());
        newUser.setGender(User.Gender.valueOf(requestDto.getGender().toUpperCase()));

        userRepository.save(newUser);

        return "회원가입이 완료되었습니다.";
    }

    @DeleteMapping("/withdraw")
    @Transactional
    public String withdraw(@RequestHeader("Authorization") String token) {
        String tokenValue = token.replace("Bearer ", "");
        Long userId = jwtUtil.extractUserId(tokenValue);

        // 1. 이 유저가 누른 좋아요들 조회
        List<Like> userLikes = likeRepository.findByUserId(userId);

        // 2. 각 좋아요마다 clothes의 likeCount 줄이기
        for (Like like : userLikes) {
            Clothes clothes = like.getClothes();
            clothes.setLikeCount(Math.max(0, clothes.getLikeCount() - 1)); // 0보다 작아지지 않게
            clothesRepository.save(clothes); // 업데이트
        }

        // 3. 좋아요 기록 삭제
        likeRepository.deleteAll(userLikes);

        // 4. 회원 삭제
        userRepository.deleteById(userId);

        return "회원 탈퇴가 완료되었습니다.";
    }
}
