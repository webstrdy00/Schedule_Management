CREATE TABLE assignees (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           name VARCHAR(255) NOT NULL,
                           email VARCHAR(255) NOT NULL UNIQUE,
                           created_at TIMESTAMP NOT NULL,
                           modified_at TIMESTAMP NOT NULL
);

CREATE TABLE schedules (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           todo VARCHAR(255) NOT NULL,
                           assignee_id BIGINT NOT NULL,
                           password VARCHAR(255) NOT NULL,
                           start_date TIMESTAMP NOT NULL,
                           end_date TIMESTAMP NOT NULL,
                           created_at TIMESTAMP NOT NULL,
                           modified_at TIMESTAMP NOT NULL,
                           FOREIGN KEY (assignee_id) REFERENCES assignees(id)
);