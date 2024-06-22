-- Criar a tabela 'resident'
CREATE TABLE resident (
    person_cpf VARCHAR(14) REFERENCES person(cpf) ON DELETE CASCADE,
    holder_cpf VARCHAR(14) REFERENCES holder(holder_cpf) ON DELETE CASCADE,
    PRIMARY KEY (person_cpf, holder_cpf)
);