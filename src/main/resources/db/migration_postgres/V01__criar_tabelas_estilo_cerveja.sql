CREATE TABLE public.estilo
(
   codigo SERIAL NOT NULL, 
   nome VARCHAR(50) NOT NULL, 
   PRIMARY KEY (codigo)
);

CREATE TABLE cerveja (
    codigo SERIAL NOT NULL,
    sku VARCHAR(50) NOT NULL,
    nome VARCHAR(80) NOT NULL,
    descricao TEXT NOT NULL,
    valor NUMERIC(10, 2) NOT NULL,
    teor_alcoolico NUMERIC(10, 2) NOT NULL,
    comissao NUMERIC(10, 2) NOT NULL,
    sabor VARCHAR(50) NOT NULL,
    origem VARCHAR(50) NOT NULL,
    codigo_estilo INTEGER NOT NULL,
    PRIMARY KEY (codigo),
    FOREIGN KEY (codigo_estilo) REFERENCES estilo(codigo)
);

INSERT INTO estilo (nome) VALUES ('Amber Lager');
INSERT INTO estilo (nome) VALUES ('Dark Lager');
INSERT INTO estilo (nome) VALUES ('Pale Lager');
INSERT INTO estilo (nome) VALUES ('Pilsner');