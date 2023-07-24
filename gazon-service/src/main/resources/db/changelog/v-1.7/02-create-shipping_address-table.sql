CREATE TABLE shipping_address
(
    shipping_address_id BIGSERIAL PRIMARY KEY,
    address             VARCHAR(255) NOT NULL,
    directions          VARCHAR(255)
);

ALTER SEQUENCE shipping_address_shipping_address_id_seq RESTART WITH 10;
