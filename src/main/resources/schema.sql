
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

create table if not exists languages (
    id BIGSERIAL primary key,
    name varchar(100) not null
);

create table if not exists attestat_types (
    id BIGSERIAL primary key,
    name varchar(100) not null
);

create table if not exists levels (
    id BIGSERIAL primary key,
    name varchar(100) not null
);

create table if not exists faculties (
    id BIGSERIAL primary key,
    level_id BIGINT not null,
    name varchar(100) not null,
    CONSTRAINT UK_faculty_level_id FOREIGN KEY (level_id) REFERENCES levels (id)
);

create table if not exists courses (
    id BIGSERIAL primary key,
    code varchar(50) not null,
    faculty_id BIGINT not null,
    name varchar(255) not null,
    CONSTRAINT UK_courses_code UNIQUE (code),
    CONSTRAINT UK_courses_faculty_id FOREIGN KEY (faculty_id) REFERENCES faculties (id)
);

create table if not exists addresses (
    id BIGSERIAL primary key,
    country_id BIGINT not null,
    name varchar(100) not null,
    CONSTRAINT UK_address_country_id FOREIGN KEY (country_id) REFERENCES country (id)
);

create table if not exists schools (
    id BIGSERIAL primary key,
    address_id BIGINT not null,
    name varchar(255) not null,
    CONSTRAINT UK_schools_address_id FOREIGN KEY (address_id) REFERENCES addresses (id)
);

create table if not exists education_info (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(100) NOT NULL UNIQUE,
    level_id BIGINT NOT NULL,
    course_id BIGINT NOT NULL,
    country_id BIGINT NOT NULL,
    address_id BIGINT NOT NULL,
    school_id BIGINT NOT NULL,
    language_id BIGINT NOT NULL,
    attestat_type_id BIGINT NOT NULL,
    attestat_no VARCHAR(20) NOT NULL,
    given_date DATE NOT NULL,
    document_image_1 oid,
    document_image_2 oid,
    attend_ort boolean,
    registration_number VARCHAR(20),
    exam_score double precision,
    issue_date DATE,
    ort_certificate_image oid,
    CONSTRAINT UK_education_info_email UNIQUE (email)
);

create table if not exists contacts (
    id BIGSERIAL primary key,
    email varchar(100) not null,
    type varchar(100) not null,
    contact varchar(100) not null
);

