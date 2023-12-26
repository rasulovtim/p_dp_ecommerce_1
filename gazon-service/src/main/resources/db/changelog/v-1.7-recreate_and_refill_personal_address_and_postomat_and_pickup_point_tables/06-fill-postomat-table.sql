INSERT INTO shipping_address (shipping_address_id, address, directions)
VALUES (4, 'Gagarinskiy Pereulok, 65, Moscow, Russia', 'Inside the supermarket');
INSERT INTO postomat (shipping_address_id, shelf_life_days)
VALUES (4, 15);

INSERT INTO shipping_address (shipping_address_id, address)
VALUES (5, 'Ulitsa Surikova, 31, Yekaterinburg, Sverdlovsk Oblast, Russia');
INSERT INTO postomat (shipping_address_id, shelf_life_days)
VALUES (5, 1);

INSERT INTO shipping_address (shipping_address_id, address)
VALUES (6, 'Ligovsky Ave, 76, St Petersburg, Russia');
INSERT INTO postomat (shipping_address_id, shelf_life_days)
VALUES (6, 30);
