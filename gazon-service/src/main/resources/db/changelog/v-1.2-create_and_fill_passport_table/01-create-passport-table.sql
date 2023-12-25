CREATE TABLE passport (
    id BIGSERIAL PRIMARY KEY,
    citizenship VARCHAR(12) NOT NULL,
    first_name VARCHAR(15) NOT NULL,
    last_name VARCHAR(25) NOT NULL,
    patronym VARCHAR(25),
    birth_date DATE NOT NULL,
    issue_date DATE NOT NULL,
    passport_number VARCHAR(11) NOT NULL,
    issuer VARCHAR(255) NOT NULL,
    issuer_number VARCHAR(7) NOT NULL
);