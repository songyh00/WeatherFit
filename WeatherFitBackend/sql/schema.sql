-- weatherfit DB 스키마
CREATE DATABASE weatherfit;
USE weatherfit;



-- clothes 테이블 스키마
CREATE TABLE clothes (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         category VARCHAR(20) NOT NULL,      -- '아우터', '상의', '원피스', '하의'
                         name VARCHAR(100) NOT NULL,          -- 옷 이름 (예: '카고 팬츠(남1)')
                         gender VARCHAR(10) NOT NULL,         -- 'MALE', 'FEMALE', 'UNISEX'
                         image_url VARCHAR(255) NOT NULL,     -- 이미지 링크
                         min_temperature INT NOT NULL,        -- 추천 최소 기온
                         max_temperature INT NOT NULL,        -- 추천 최대 기온
                         like_count INT DEFAULT 0             -- 좋아요 수 (기본 0)
);


-- users 테이블 스키마
CREATE TABLE users (
                       id INT PRIMARY KEY AUTO_INCREMENT,          -- 회원 ID (자동 증가)
                       username VARCHAR(50) UNIQUE NOT NULL,        -- 사용자 이름 (중복 불가)
                       password VARCHAR(255) NOT NULL,              -- 비밀번호 (해시 저장 필요)
                       email VARCHAR(100) UNIQUE NOT NULL,          -- 이메일 (중복 불가)
                       gender ENUM('MALE', 'FEMALE') NOT NULL       -- 성별 (남자, 여자만)
);


-- clothes_like 테이블 스키마
CREATE TABLE clothes_like (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              users_id BIGINT NOT NULL,     -- 누른 사람(회원 ID)
                              clothes_id BIGINT NOT NULL,    -- 좋아요 누른 옷 ID
                              created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- 좋아요 누른 시간
                              UNIQUE (users_id, clothes_id) -- 하나의 회원이 같은 옷에 여러 번 누르지 못하게
);

