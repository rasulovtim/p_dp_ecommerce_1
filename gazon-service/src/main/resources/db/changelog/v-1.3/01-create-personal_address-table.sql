CREATE TABLE personal_address (
     id BIGSERIAL PRIMARY KEY,
     address VARCHAR(255) NOT NULL,
     directions VARCHAR(255),
     apartment VARCHAR(255) NOT NULL,
     floor VARCHAR(10) NOT NULL,
     entrance VARCHAR(255) NOT NULL,
     door_code VARCHAR(50),
     post_code VARCHAR(12)
);