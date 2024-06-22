-- Criar a tabela 'person'
CREATE TABLE person (
    cpf VARCHAR(14) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    birthday DATE,
    cnh VARCHAR(20),
    photo_url VARCHAR(255),
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL
);

-- Criar a tabela 'phone_numbers' que armazena os números de telefone associados a uma pessoa
CREATE TABLE phone_numbers (
   person_cpf VARCHAR(14) REFERENCES person(cpf) ON DELETE CASCADE,
   phone_number VARCHAR(20),
   PRIMARY KEY (person_cpf, phone_number)
);
