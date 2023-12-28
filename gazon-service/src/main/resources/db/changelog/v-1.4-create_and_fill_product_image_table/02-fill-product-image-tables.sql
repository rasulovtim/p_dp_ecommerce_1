INSERT INTO product (name, stock_count, description, is_adult, code, weight, price)
VALUES ('product1', 1, 'product A', true, '1', 1, 1);
INSERT INTO product (name, stock_count, description, is_adult, code, weight, price)
VALUES ('product2', 2, 'product B', true, '2', 2, 2);
INSERT INTO product (name, stock_count, description, is_adult, code, weight, price)
VALUES ('product3', 3, 'product C', true, '3', 3, 3);


INSERT INTO product_image (product_id, name, data)
VALUES (1, 'product image1', '\\'::bytea);
INSERT INTO product_image (product_id, name, data)
VALUES (1, 'product image2', '\001'::bytea);
INSERT INTO product_image (product_id, name, data)
VALUES (3, 'product image3', '\\'::bytea);
INSERT INTO product_image (product_id, name, data)
VALUES (3, 'product image4', '\001'::bytea);
