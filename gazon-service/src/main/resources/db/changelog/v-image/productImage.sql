CREATE TABLE product (
  id BIGSERIAL PRIMARY KEY
);

CREATE TABLE image (
  id BIGSERIAL PRIMARY KEY,
  product_id BIGINT REFERENCES product(id),
  name VARCHAR(255),
  data BYTEA
);