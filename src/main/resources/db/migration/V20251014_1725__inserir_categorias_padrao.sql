-- Migration: inserir categorias padrao
-- Criado em: Tue Oct 14 17:25:31 -03 2025

-- Inserir categorias padrão (somente se ainda não existirem)
INSERT INTO categoria (tipo_categoria, limite_gastos)
SELECT 'Alimentação', 1000.00
WHERE NOT EXISTS (SELECT 1 FROM categoria WHERE tipo_categoria = 'Alimentação');

INSERT INTO categoria (tipo_categoria, limite_gastos)
SELECT 'Transporte', 800.00
WHERE NOT EXISTS (SELECT 1 FROM categoria WHERE tipo_categoria = 'Transporte');

INSERT INTO categoria (tipo_categoria, limite_gastos)
SELECT 'Lazer', 600.00
WHERE NOT EXISTS (SELECT 1 FROM categoria WHERE tipo_categoria = 'Lazer');

INSERT INTO categoria (tipo_categoria, limite_gastos)
SELECT 'Saúde', 1200.00
WHERE NOT EXISTS (SELECT 1 FROM categoria WHERE tipo_categoria = 'Saúde');
