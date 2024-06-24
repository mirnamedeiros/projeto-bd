-- Create table for apartment
CREATE TABLE apartment (
    number INTEGER NOT NULL,
    block INTEGER NOT NULL,
    condominium_id INTEGER REFERENCES condominium(id),
    PRIMARY KEY (number, block)
);