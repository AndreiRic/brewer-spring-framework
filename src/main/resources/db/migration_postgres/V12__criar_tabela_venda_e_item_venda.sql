CREATE TABLE venda (
    codigo SERIAL NOT NULL,
    data_criacao TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    valor_frete NUMERIC(10,2),
    valor_desconto NUMERIC(10,2),
    valor_total NUMERIC(10,2) NOT NULL,
    status VARCHAR(30) NOT NULL,
    observacao VARCHAR(200),
    data_hora_entrega TIMESTAMP WITHOUT TIME ZONE,
    codigo_cliente INTEGER NOT NULL,
    codigo_usuario INTEGER NOT NULL,
    PRIMARY KEY (codigo),
    FOREIGN KEY (codigo_cliente) REFERENCES cliente(codigo),
    FOREIGN KEY (codigo_usuario) REFERENCES usuario(codigo)
);

CREATE TABLE item_venda (
    codigo SERIAL NOT NULL,
    quantidade INTEGER NOT NULL,
    valor_unitario DECIMAL(10,2) NOT NULL,
    codigo_cerveja INTEGER NOT NULL,
    codigo_venda INTEGER NOT NULL,
    PRIMARY KEY (codigo),
    FOREIGN KEY (codigo_cerveja) REFERENCES cerveja(codigo),
    FOREIGN KEY (codigo_venda) REFERENCES venda(codigo)
);