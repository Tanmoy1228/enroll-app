
-- Create user table
create table if not exists users (
    id BIGSERIAL primary key,
    email varchar(100) not null,
    password varchar(1000) not null,
    status varchar(20) not null,
    CONSTRAINT UK_users_email UNIQUE (email)
);

create table if not exists country (
    id BIGSERIAL primary key,
    name varchar(100) not null
);

create table if not exists nationality (
    id BIGSERIAL primary key,
    name varchar(100) not null
);

create table if not exists basic_info (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(100) NOT NULL UNIQUE,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    native_name VARCHAR(255) NOT NULL,
    native_surname VARCHAR(255) NOT NULL,
    patronymic VARCHAR(255),
    date_of_birth DATE NOT NULL,
    citizenship_id BIGINT NOT NULL,
    nationality_id BIGINT NOT NULL,
    gender VARCHAR(20) NOT NULL,
    marital_status VARCHAR(20) NOT NULL,
    iin VARCHAR(100) NOT NULL,
    military_registration VARCHAR(255),
    document_type VARCHAR(100) NOT NULL,
    document_no VARCHAR(100) NOT NULL,
    issued_by VARCHAR(255) NOT NULL,
    issuance_date DATE NOT NULL,
    document_image_1 oid,
    document_image_2 oid,
    CONSTRAINT UK_basic_info_email UNIQUE (email)
);


