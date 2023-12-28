CREATE TABLE IF NOT EXISTS orders (
    id BIGSERIAL PRIMARY KEY,
    shipping_address_id BIGINT REFERENCES shipping_address (shipping_address_id) ON DELETE CASCADE NOT NULL,
    shipping_date DATE NOT NULL,
    order_code VARCHAR NOT NULL,
    create_date_time TIMESTAMP NOT NULL,
    sum DECIMAL NOT NULL,
    discount DECIMAL NOT NULL,
    bag_counter SMALLINT NOT NULL,
    user_id    BIGINT REFERENCES users (id) ON DELETE CASCADE NOT NULL,
    order_status VARCHAR(20)
);

