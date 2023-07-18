CREATE TABLE product
(

    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(255)   NOT NULL,
    stock_count INTEGER        NOT NULL,
    description VARCHAR(255)   NOT NULL,
    is_adult    BOOLEAN        NOT NULL,
    code        VARCHAR(255)   NOT NULL,
    weight      BIGINT         NOT NULL,
    price       NUMERIC(20, 2) NOT NULL
);

CREATE TABLE product_image
(

    id         BIGSERIAL PRIMARY KEY,
    product_id BIGINT REFERENCES product (id) NOT NULL,
    name       VARCHAR(255) NOT NULL,
    data       BYTEA NOT NULL
);