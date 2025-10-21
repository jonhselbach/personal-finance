-- Migration: create fixes category data
-- Criado em: Mon Oct 20 21:40:58 -03 2025

INSERT INTO category (type_category, limit_spent)
SELECT 'Alimentação', 1000.00
WHERE NOT EXISTS (SELECT 1 FROM category WHERE type_category = 'Alimentação');

INSERT INTO category (type_category, limit_spent)
SELECT 'Transporte', 800.00
WHERE NOT EXISTS (SELECT 1 FROM category WHERE type_category = 'Transporte');

INSERT INTO category (type_category, limit_spent)
SELECT 'Lazer', 600.00
WHERE NOT EXISTS (SELECT 1 FROM category WHERE type_category = 'Lazer');

INSERT INTO category (type_category, limit_spent)
SELECT 'Saúde', 1200.00
WHERE NOT EXISTS (SELECT 1 FROM category WHERE type_category = 'Saúde');
