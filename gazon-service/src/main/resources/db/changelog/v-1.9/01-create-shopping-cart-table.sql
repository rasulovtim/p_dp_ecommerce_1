CREATE TABLE IF NOT EXISTS shopping_cart (
                                             id BIGSERIAL PRIMARY KEY,
                                             user_id BIGINT NOT NULL,
                                             sum DECIMAL(10, 2) NOT NULL,
    total_weight BIGINT NOT NULL
    );

