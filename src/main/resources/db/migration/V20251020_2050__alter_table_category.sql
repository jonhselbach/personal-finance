-- Migration: alter table category
-- Criado em: Mon Oct 20 20:50:17 -03 2025


CREATE TABLE category (
    id BIGSERIAL PRIMARY KEY,
    type_category VARCHAR(50) NOT NULL UNIQUE,
    limit_spent NUMERIC(10,2) NOT NULL
);
