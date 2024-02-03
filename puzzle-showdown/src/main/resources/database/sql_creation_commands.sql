CREATE DATABASE employee_management;
CREATE TABLE employees;
CREATE TABLE employees (
    id SERIAL,
    name TEXT NOT NULL,
    level TEXT NOT NULL,
    hired TIMESTAMP NOT NULL)
;