INSERT INTO shipping_address (shipping_address_id, address, directions)
VALUES (7, 'Ulitsa Yakovleva, 51, Omsk, Omsk Oblast, Russia', 'Inside the supermarket');
INSERT INTO pickup_point (shipping_address_id, shelf_life_days)
VALUES (7, 15);

INSERT INTO shipping_address (shipping_address_id, address)
VALUES (8, 'Ulitsa 8 Marta, 59, Yekaterinburg, Sverdlovsk Oblast, Russia');
INSERT INTO pickup_point (shipping_address_id, shelf_life_days)
VALUES (8, 1);

INSERT INTO shipping_address (shipping_address_id, address)
VALUES (9, 'Ulitsa Ostozhenka, 5, Moscow, Russia');
INSERT INTO pickup_point (shipping_address_id, shelf_life_days)
VALUES (9, 30);
