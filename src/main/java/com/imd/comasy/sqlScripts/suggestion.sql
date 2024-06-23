CREATE TABLE suggestion (
    suggestion_id SERIAL PRIMARY KEY,
    type VARCHAR(255) NOT NULL,
    message TEXT NOT NULL,
    qtd_votos INTEGER DEFAULT,
    data_proposta DATE NOT NULL,
    resident_id INTEGER REFERENCES resident(resident_id),
    active BOOLEAN DEFAULT TRUE
);