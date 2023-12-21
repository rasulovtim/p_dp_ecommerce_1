CREATE TABLE pickup_point (
    id BIGSERIAL PRIMARY KEY,
    address VARCHAR(255) NOT NULL,
    directions VARCHAR(255),
    shelf_life_days SMALLINT NOT NULL
);

CREATE TABLE pickup_point_features_to_pickup_point (
    pickup_point_id BIGSERIAL NOT NULL,
    pickup_point_feature VARCHAR(255) NOT NULL,
    foreign key (pickup_point_id) references pickup_point(id)

);