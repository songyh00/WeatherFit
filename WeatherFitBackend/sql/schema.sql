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

