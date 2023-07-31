INSERT INTO shopping_cart_selected_products (shopping_cart_id, selected_products)
SELECT 1, 'product1' WHERE NOT EXISTS (SELECT 1 FROM shopping_cart_selected_products WHERE shopping_cart_id = 1 AND selected_products = 'product1');
INSERT INTO shopping_cart_selected_products (shopping_cart_id, selected_products)
SELECT 1, 'product2' WHERE NOT EXISTS (SELECT 1 FROM shopping_cart_selected_products WHERE shopping_cart_id = 1 AND selected_products = 'product2');
INSERT INTO shopping_cart_selected_products (shopping_cart_id, selected_products)
SELECT 2, 'product3' WHERE NOT EXISTS (SELECT 1 FROM shopping_cart_selected_products WHERE shopping_cart_id = 2 AND selected_products = 'product3');