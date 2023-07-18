CREATE TABLE users
(
    id                BIGSERIAL PRIMARY KEY ,
    email             VARCHAR(255) NOT NULL,
    password          VARCHAR(255) NOT NULL,
    security_question VARCHAR(255) NOT NULL,
    answer_question   VARCHAR(255) NOT NULL,
    first_name        VARCHAR(255) NOT NULL,
    last_name         VARCHAR(255) NOT NULL,
    birth_date        DATE         NOT NULL,
    gender            VARCHAR(255) NOT NULL,
    phone_number      VARCHAR(255) NOT NULL,
    passport          VARCHAR(255),
    personal_address  VARCHAR(255),
    create_date       DATE         NOT NULL,
    bank_cards        VARCHAR(255),
    roles             VARCHAR(255)

);