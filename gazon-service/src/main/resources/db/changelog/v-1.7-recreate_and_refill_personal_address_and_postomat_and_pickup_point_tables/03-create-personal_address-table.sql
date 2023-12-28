CREATE TABLE personal_address
(
    shipping_address_id BIGINT PRIMARY KEY,
    apartment           VARCHAR(255) NOT NULL,
    floor               VARCHAR(10)  NOT NULL,
    entrance            VARCHAR(255) NOT NULL,
    door_code           VARCHAR(50),
    post_code           VARCHAR(12)
);

ALTER TABLE personal_address
    ADD CONSTRAINT FK_personal_address
        FOREIGN KEY (shipping_address_id)
            REFERENCES shipping_address (shipping_address_id);

