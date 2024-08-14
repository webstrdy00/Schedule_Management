CREATE TABLE schedules (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           todo VARCHAR(255) NOT NULL,
                           assignee VARCHAR(255) NOT NULL,
                           password VARCHAR(255) NOT NULL,
                           start_date DATETIME NOT NULL,
                           end_date DATETIME NOT NULL,
                           created_at DATETIME NOT NULL,
                           modified_at DATETIME NOT NULL
);