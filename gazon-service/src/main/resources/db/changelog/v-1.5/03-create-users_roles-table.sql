create table users_roles
(
    user_id BIGSERIAL not null,
    role_id BIGSERIAL not null,
    primary key (user_id, role_id),
    foreign key (user_id) references users (id),
    foreign key (role_id) references roles (id)
);