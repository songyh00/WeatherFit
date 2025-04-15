package com.weatherfit.backend.user.repository;

import com.weatherfit.backend.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * 사용자(User) 리포지토리
 * - 회원 정보 조회 및 저장을 담당
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * username(아이디)로 회원 찾기
     *
     * @param username 사용자 로그인 ID
     * @return 사용자 정보 (Optional)
     */
    Optional<User> findByUsername(String username);

    /**
     * email로 회원 찾기
     *
     * @param email 사용자 이메일
     * @return 사용자 정보 (Optional)
     */
    Optional<User> findByEmail(String email);
}
