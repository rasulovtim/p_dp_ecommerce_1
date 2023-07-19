CREATE TABLE pickup_point
(
    shipping_address_id BIGINT PRIMARY KEY,
    shelf_life_days     SMALLINT NOT NULL
);

ALTER TABLE pickup_point
    ADD CONSTRAINT FK_pickup_point
        FOREIGN KEY (shipping_address_id)
            REFERENCES shipping_address (shipping_address_id);


CREATE TABLE pickup_point_features_to_pickup_point
(
    pickup_point_id  BIGSERIAL    NOT NULL,
    pickup_point_feature VARCHAR(255) NOT NULL,
    foreign key (pickup_point_id) references pickup_point (shipping_address_id)

);