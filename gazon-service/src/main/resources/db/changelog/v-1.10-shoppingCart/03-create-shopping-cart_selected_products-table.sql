CREATE TABLE shopping_cart_selected_products (
                                                 id SERIAL PRIMARY KEY,
                                                 shopping_cart_id BIGINT REFERENCES shopping_cart (id) ON DELETE CASCADE,
                                                 selected_product_id BIGINT REFERENCES selected_product (id) ON DELETE CASCADE,
                                                 selected_products TEXT
);