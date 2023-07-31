CREATE TABLE IF NOT EXISTS shopping_cart_selected_products (
                                                               shopping_cart_id BIGINT NOT NULL,
                                                               selected_products VARCHAR(255) NOT NULL,
                                                               PRIMARY KEY (shopping_cart_id, selected_products),
                                                               CONSTRAINT fk_shopping_cart_id FOREIGN KEY (shopping_cart_id) REFERENCES shopping_cart (id)
);

