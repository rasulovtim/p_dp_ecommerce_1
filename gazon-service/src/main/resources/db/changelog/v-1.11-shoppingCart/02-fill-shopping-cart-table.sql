INSERT INTO shopping_cart (user_id)
VALUES (1), (2), (3);

ALTER TABLE selected_product
    ADD COLUMN shopping_cart_id BIGINT REFERENCES shopping_cart (id) ON DELETE CASCADE;

UPDATE selected_product
SET shopping_cart_id = (SELECT shopping_cart.id FROM shopping_cart WHERE shopping_cart.user_id = 1)
WHERE product_id = 1;

UPDATE selected_product
SET shopping_cart_id = (SELECT shopping_cart.id FROM shopping_cart WHERE shopping_cart.user_id = 2)
WHERE product_id = 2;

UPDATE selected_product
SET shopping_cart_id = (SELECT shopping_cart.id FROM shopping_cart WHERE shopping_cart.user_id = 3)
WHERE product_id = 3;
