CREATE TABLE postomat
(
    shipping_address_id BIGINT PRIMARY KEY,
    shelf_life_days     SMALLINT NOT NULL
);

ALTER TABLE postomat
    ADD CONSTRAINT FK_postomat
        FOREIGN KEY (shipping_address_id)
            REFERENCES shipping_address (shipping_address_id);
