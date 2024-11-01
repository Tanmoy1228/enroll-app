
-- Create user table
create table if not exists users (
    id BIGSERIAL primary key,
    email varchar(100) not null,
    password varchar(1000) not null,
    status varchar(20) not null,
    CONSTRAINT UK_users_email UNIQUE (email)
);
