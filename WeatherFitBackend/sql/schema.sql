CREATE TABLE outfit (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        image_url VARCHAR(255) NOT NULL,
                        min_temperature INT NOT NULL,
                        max_temperature INT NOT NULL,
                        style VARCHAR(50) NOT NULL,
                        gender VARCHAR(10) NOT NULL,
                        is_light BOOLEAN DEFAULT FALSE
);
