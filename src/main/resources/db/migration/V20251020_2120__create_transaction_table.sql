-- Migration: create transaction table
-- Criado em: Mon Oct 20 21:20:46 -03 2025

CREATE TABLE transactions (
    id SERIAL PRIMARY KEY,
    amount NUMERIC NOT NULL,
    type VARCHAR(10) NOT NULL,
    idcategory BIGINT REFERENCES category(id),
    date DATE NOT NULL,
    description TEXT
);
