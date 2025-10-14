CREATE TABLE categoria (
    id BIGSERIAL PRIMARY KEY,
    tipo_categoria VARCHAR(50) NOT NULL UNIQUE,
    limite_gastos NUMERIC(10,2) NOT NULL
);