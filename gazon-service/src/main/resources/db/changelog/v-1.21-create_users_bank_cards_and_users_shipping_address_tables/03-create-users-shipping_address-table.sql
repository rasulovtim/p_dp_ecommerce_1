create table users_shipping_address
(
    user_id                  BIGSERIAL not null,
    user_shipping_address_id BIGSERIAL not null,
    primary key (user_id, user_shipping_address_id),
    foreign key (user_id) references users (id),
    foreign key (user_shipping_address_id) references shipping_address (shipping_address_id)
);