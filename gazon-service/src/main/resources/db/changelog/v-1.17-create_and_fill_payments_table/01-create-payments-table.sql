DROP TABLE IF EXISTS payment;

CREATE TABLE payments (
    id BIGSERIAL PRIMARY KEY,
    bank_card_id BIGINT REFERENCES bank_card (id) NOT NULL,
    payment_status VARCHAR(255) NOT NULL,
    create_date_time TIMESTAMP NOT NULL,
    order_id BIGINT REFERENCES orders (id) NOT NULL,
    sum DECIMAL NOT NULL,
    user_id BIGINT REFERENCES users (id) NOT NULL
);
