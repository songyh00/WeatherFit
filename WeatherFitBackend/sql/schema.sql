-- clothes 테이블 스키마
CREATE TABLE clothes (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         category VARCHAR(20) NOT NULL,      -- '아우터', '상의', '하의'
                         name VARCHAR(100) NOT NULL,          -- 옷 이름 (예: '카고 팬츠(남1)')
                         gender VARCHAR(10) NOT NULL,         -- 'MALE', 'FEMALE', 'UNISEX'
                         image_url VARCHAR(255) NOT NULL,     -- 이미지 링크
                         min_temperature INT NOT NULL,        -- 추천 최소 기온
                         max_temperature INT NOT NULL         -- 추천 최대 기온
);

-- outfit 테이블 스키마
CREATE TABLE outfit (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        style VARCHAR(20) NOT NULL,          -- 스타일명 (예: '스트릿')
                        name VARCHAR(100) NOT NULL,          -- 코디 이름 (예: '스트릿코디(남1/매우추움)')
                        gender VARCHAR(10) NOT NULL,         -- 'MALE', 'FEMALE'
                        image_url VARCHAR(255) NOT NULL,     -- 코디 이미지 링크
                        min_temperature INT NOT NULL,        -- 추천 최소 기온
                        max_temperature INT NOT NULL         -- 추천 최대 기온
);