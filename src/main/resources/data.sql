

TRUNCATE TABLE country CASCADE;
ALTER SEQUENCE country_id_seq RESTART WITH 1;

TRUNCATE TABLE nationality CASCADE;
ALTER SEQUENCE nationality_id_seq RESTART WITH 1;

TRUNCATE TABLE languages CASCADE;
ALTER SEQUENCE languages_id_seq RESTART WITH 1;

TRUNCATE TABLE attestat_types CASCADE;
ALTER SEQUENCE attestat_types_id_seq RESTART WITH 1;

TRUNCATE TABLE courses CASCADE;
ALTER SEQUENCE courses_id_seq RESTART WITH 1;

TRUNCATE TABLE faculties CASCADE;
ALTER SEQUENCE faculties_id_seq RESTART WITH 1;

TRUNCATE TABLE levels CASCADE;
ALTER SEQUENCE levels_id_seq RESTART WITH 1;

TRUNCATE TABLE schools CASCADE;
ALTER SEQUENCE schools_id_seq RESTART WITH 1;

TRUNCATE TABLE addresses CASCADE;
ALTER SEQUENCE addresses_id_seq RESTART WITH 1;

insert into country(name) values ('Bangladesh');
insert into country(name) values ('Russia');
insert into country(name) values ('Kyrgyzstan');
insert into country(name) values ('Australia');
insert into country(name) values ('Nepal');


insert into nationality(name) values ('Bangladeshi');
insert into nationality(name) values ('Russian');
insert into nationality(name) values ('Kyrgyz');
insert into nationality(name) values ('Australian');
insert into nationality(name) values ('Nepalese');


insert into languages(name) values ('Bengali');
insert into languages(name) values ('Russian');
insert into languages(name) values ('Kyrgyz');
insert into languages(name) values ('English');
insert into languages(name) values ('Nepali');
insert into languages(name) values ('Turkish');
insert into languages(name) values ('Spanish');


insert into attestat_types(name) values ('Certificate');
insert into attestat_types(name) values ('Certificate with distinction');
insert into attestat_types(name) values ('Diploma');
insert into attestat_types(name) values ('Diploma with distinction');


insert into levels(name) values ('College');
insert into levels(name) values ('Bachelor');

insert into faculties(level_id, name) values (1, 'SECONDARY VOCATIONAL EDUCATION');
insert into faculties(level_id, name) values (2, 'FACULTY OF MEDICINE');
insert into faculties(level_id, name) values (2, 'FACULTY OF HUMANITIES');
insert into faculties(level_id, name) values (2, 'FACULTY OF ENGINEERING AND INFORMATICS');


insert into courses(faculty_id, code, name) values (1, '110001', 'Software of computer facilities and automated system');
insert into courses(faculty_id, code, name) values (1, '110002', 'Information Technologies and Information System');
insert into courses(faculty_id, code, name) values (1, '110003', 'Economics and Accounting');
insert into courses(faculty_id, code, name) values (1, '110004', 'Marketing');
insert into courses(faculty_id, code, name) values (2, '560001', 'General Medicine');
insert into courses(faculty_id, code, name) values (2, '560002', 'Pediatry');
insert into courses(faculty_id, code, name) values (3, '531100', 'Linguistics');
insert into courses(faculty_id, code, name) values (3, '530300', 'Psychology');
insert into courses(faculty_id, code, name) values (3, '530600', 'Journalism');
insert into courses(faculty_id, code, name) values (4, '710100', 'Cybersecurity and Ethical Hacking');
insert into courses(faculty_id, code, name) values (4, '531101', 'Software Engineering');
insert into courses(faculty_id, code, name) values (4, '531102', 'Artificial Intelligence and Robotics');


insert into addresses(country_id, name) values (1, 'Dhaka, Bangladesh');
insert into addresses(country_id, name) values (1, 'Khulna, Bangladesh');
insert into addresses(country_id, name) values (2, 'Moscow, Russia');
insert into addresses(country_id, name) values (2, 'Surgut, Russia');
insert into addresses(country_id, name) values (3, 'Batken, Kyrgyzstan');
insert into addresses(country_id, name) values (3, 'Leilek, Kyrgyzstan');
insert into addresses(country_id, name) values (4, 'Thamel, Nepal');
insert into addresses(country_id, name) values (4, 'Pokhra, Nepal');
insert into addresses(country_id, name) values (5, 'Melbourne, Australia');
insert into addresses(country_id, name) values (5, 'Sydney, Australia');


insert into schools(address_id, name) values (1, 'International Hope School, Dhaka');
insert into schools(address_id, name) values (1, 'Metropolitan College, Dhaka');
insert into schools(address_id, name) values (2, 'Milestone College, Khulna');
insert into schools(address_id, name) values (2, 'Ispahani Public School and College, Khulna');

insert into schools(address_id, name) values (3, 'International Hope School, Moscow');
insert into schools(address_id, name) values (3, 'Metropolitan College, Moscow');
insert into schools(address_id, name) values (4, 'Milestone College, Surgut');
insert into schools(address_id, name) values (4, 'Ispahani Public School and College, Surgut');

insert into schools(address_id, name) values (5, 'International Hope School, Batken');
insert into schools(address_id, name) values (5, 'Metropolitan College, Batken');
insert into schools(address_id, name) values (6, 'Milestone College, Leilek');
insert into schools(address_id, name) values (6, 'Ispahani Public School and College, Leilek');

insert into schools(address_id, name) values (7, 'International Hope School, Thamel');
insert into schools(address_id, name) values (7, 'Metropolitan College, Thamel');
insert into schools(address_id, name) values (8, 'Milestone College, Pokhra');
insert into schools(address_id, name) values (8, 'Ispahani Public School and College, Pokhra');

insert into schools(address_id, name) values (9, 'International Hope School, Melbourne');
insert into schools(address_id, name) values (9, 'Metropolitan College, Melbourne');
insert into schools(address_id, name) values (10, 'Milestone College, Sydney');
insert into schools(address_id, name) values (10, 'Ispahani Public School and College, Sydney');


