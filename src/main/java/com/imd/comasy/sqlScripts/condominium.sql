-- Create table for condominium
CREATE TABLE condominium (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    cnpj VARCHAR(14) NOT NULL,
    cep VARCHAR(8) NOT NULL,
    neighborhood VARCHAR(100) NOT NULL,
    street VARCHAR(255) NOT NULL,
    city VARCHAR(100) NOT NULL,
    state VARCHAR(2) NOT NULL,
    phoneNumber VARCHAR(20)
);