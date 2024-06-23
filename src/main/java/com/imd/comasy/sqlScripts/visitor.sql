-- Criar a tabela 'visitor'
CREATE TABLE visitor (
    code SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    type VARCHAR(50) NOT NULL
);
