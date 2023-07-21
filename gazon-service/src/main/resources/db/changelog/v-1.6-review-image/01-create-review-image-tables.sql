CREATE TABLE review
(

    id                  BIGSERIAL PRIMARY KEY,
    product_id          BIGINT REFERENCES product (id) ON DELETE CASCADE NOT NULL,
    create_date         DATE default current_date,
    pros                VARCHAR(255)                                     NOT NULL,
    cons                VARCHAR(255)                                     NOT NULL,
    comment             VARCHAR(255),
    rating              INT2                                             NOT NULL,
    helpful_counter     INTEGER,
    not_helpful_counter INTEGER
);

CREATE TABLE review_image
(

    id        BIGSERIAL PRIMARY KEY,
    review_id BIGINT REFERENCES review (id) ON DELETE CASCADE NOT NULL,
    name      VARCHAR(255)                                    NOT NULL,
    data      BYTEA                                           NOT NULL
);