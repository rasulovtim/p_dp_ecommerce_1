CREATE TABLE bank_card (
    id BIGSERIAL PRIMARY KEY,
    card_number VARCHAR(255) NOT NULL,
    due_date DATE NOT NULL,
    security_code INTEGER NOT NULL
);