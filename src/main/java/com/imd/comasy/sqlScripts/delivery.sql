-- Criar a tabela 'delivery'
CREATE TABLE delivery (
    id SERIAL PRIMARY KEY,
    status VARCHAR(50) NOT NULL,
    resident_cpf VARCHAR(14),
    arrivalDate DATE NOT NULL,
    doorman_cpf VARCHAR(14),
    FOREIGN KEY (resident_cpf) REFERENCES person(cpf),
    FOREIGN KEY (doorman_cpf) REFERENCES doorman(cpf)
);