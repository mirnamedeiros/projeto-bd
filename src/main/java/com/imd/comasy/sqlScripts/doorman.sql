-- Criar a tabela 'doorman' com referência para 'person'
CREATE TABLE doorman (
     cpf VARCHAR(14) PRIMARY KEY,
     FOREIGN KEY (cpf) REFERENCES person(cpf)
);