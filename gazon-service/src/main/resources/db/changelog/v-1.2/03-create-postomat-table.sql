CREATE TABLE postomat (
    id BIGSERIAL PRIMARY KEY,
    address VARCHAR(255) NOT NULL,
    directions VARCHAR(255),
    shelf_life_days SMALLINT NOT NULL
);