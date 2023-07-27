INSERT INTO shipping_address (shipping_address_id, address, directions)
VALUES (1, 'Malaya Bronnaya St, Moscow, Russia', 'First entrance to the right after the alley');
INSERT INTO personal_address (shipping_address_id, apartment, floor, entrance, door_code, post_code)
VALUES (1, '404', '13', '1', '1234', '136881');

INSERT INTO shipping_address (shipping_address_id, address)
VALUES (2, 'Malyy Cherkasskiy Pereulok, Moscow, Russia');
INSERT INTO personal_address (shipping_address_id, apartment, floor, entrance)
VALUES (2, '63', '1', '1');

INSERT INTO shipping_address (shipping_address_id, address)
VALUES (3, 'Yuzhnaya St, Voronezh, Russia');
INSERT INTO personal_address (shipping_address_id, apartment, floor, entrance)
VALUES (3, '63', '1', '1');



