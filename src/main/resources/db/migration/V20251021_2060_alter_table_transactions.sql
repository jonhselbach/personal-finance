-- Migration: alter foreign key to cascade
-- Criado em: Tue Oct 21 2025

ALTER TABLE transactions
DROP CONSTRAINT IF EXISTS transactions_idcategory_fkey;

ALTER TABLE transactions
ADD CONSTRAINT transactions_idcategory_fkey
FOREIGN KEY (idcategory)
REFERENCES category (id)
ON UPDATE CASCADE
ON DELETE CASCADE;
