
CREATE TABLE selected_product
(
    id         BIGSERIAL PRIMARY KEY,
    product_id BIGINT REFERENCES product (id) ON DELETE CASCADE NOT NULL,
    count       INTEGER NOT NULL
);