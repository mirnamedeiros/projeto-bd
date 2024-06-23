-- Criar a tabela 'visit'
CREATE TABLE visit (
    doorman_cpf VARCHAR(14) NOT NULL,
    visitor_code INT NOT NULL,
    resident_cpf VARCHAR(14) NOT NULL,
    status VARCHAR(50) NOT NULL,
    arrival_date TIMESTAMP NOT NULL,
    departure_date TIMESTAMP,

    PRIMARY KEY (doorman_cpf, visitor_code, resident_cpf, arrival_date),

    FOREIGN KEY (doorman_cpf) REFERENCES doorman(cpf),
    FOREIGN KEY (visitor_code) REFERENCES visitor(code),
    FOREIGN KEY (resident_cpf) REFERENCES resident(person_cpf)
);
