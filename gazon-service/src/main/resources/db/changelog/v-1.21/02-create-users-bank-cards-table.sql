create table users_bank_cards
(
    user_id      BIGSERIAL not null,
    bank_card_id BIGSERIAL not null,
    primary key (user_id, bank_card_id),
    foreign key (user_id) references users (id),
    foreign key (bank_card_id) references bank_card (id)
);