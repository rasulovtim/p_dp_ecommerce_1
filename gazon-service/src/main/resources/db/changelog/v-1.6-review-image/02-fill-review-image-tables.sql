INSERT INTO review (product_id, pros, cons, comment, rating,
                    helpful_counter, not_helpful_counter)
VALUES (1, 'good', 'not perfect', 'no comment', 8, 7, 1);

INSERT INTO review (product_id, pros, cons, rating)
VALUES (1, 'cheap', 'low quality', 4);
INSERT INTO review (product_id, pros, cons, rating)
VALUES (3, 'pros3', 'cons3', 3);
INSERT INTO review (product_id, pros, cons, rating)
VALUES (3, 'pros4', 'cons4', 4);


INSERT INTO review_image (review_id, name, data)
VALUES (1, 'name1', '\\'::bytea);
INSERT INTO review_image (review_id, name, data)
VALUES (1, 'name2', '\001'::bytea);
INSERT INTO review_image (review_id, name, data)
VALUES (3, 'name3', '\\'::bytea);
INSERT INTO review_image (review_id, name, data)
VALUES (3, 'name4', '\001'::bytea);