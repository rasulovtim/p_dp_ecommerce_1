CREATE TABLE shopping_cart
(
    id         BIGSERIAL PRIMARY KEY,
    user_id    BIGINT REFERENCES users (id) ON DELETE CASCADE NOT NULL
);


