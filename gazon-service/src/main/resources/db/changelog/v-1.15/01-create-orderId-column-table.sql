ALTER TABLE selected_product
    ADD COLUMN order_id BIGINT REFERENCES orders (id);