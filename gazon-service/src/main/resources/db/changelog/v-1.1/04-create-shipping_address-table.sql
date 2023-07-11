CREATE TABLE shipping_address
(
    id BIGSERIAL PRIMARY KEY NOT NULL,
    address    VARCHAR(255) NOT NULL,
    directions VARCHAR(255) NOT NULL
);